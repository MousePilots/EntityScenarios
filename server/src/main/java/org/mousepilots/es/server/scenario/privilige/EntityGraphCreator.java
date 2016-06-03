/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.scenario.priviliges.Priviliges;
import org.mousepilots.es.core.util.Producer;

/**
 * Creates JPA 2.1-{@link EntityGraph}s to increase. The implementation is non blocking and thread-safe and can be re-used.
 * @author AP34WV
 */
public class EntityGraphCreator implements Serializable {
    
    private <T> void populateSubgraph(
            Producer<Class> javaTypeProducer,
            Consumer<String> nodeAdder,
            Function<String,Subgraph> keySubgrapAdder, 
            Function<String,Subgraph> valueSubgrapAdder, 
            Priviliges priviliges, 
            CRUD operation, 
            Set<AttributeES> visited)
    {
        final ManagedTypeES managedType = AbstractMetamodelES.getInstance().managedType(javaTypeProducer.produce());
        final Set<AttributeES> attributes = priviliges.getAttributes(operation, managedType);
        final AttributeVisitor<Void,Void> attributeVisitor = new AttributeVisitor<Void, Void>(){
            
            private Void visitAttribute(
                    AttributeES a, 
                    Producer<TypeES> targetTypeProducer, 
                    Consumer<String> nodeAdder, 
                    Function<String,Subgraph> subgraphAdder){
                if(targetTypeProducer.produce() instanceof IdentifiableTypeES){
                    final Subgraph subgraph = subgraphAdder.apply(a.getName());
                    populateSubgraph(
                        subgraph::getClassType, 
                        subgraph::addAttributeNodes, 
                        subgraph::addKeySubgraph, 
                        subgraph::addSubgraph, 
                        priviliges, 
                        operation, 
                        visited
                    );
                } else {
                    if(nodeAdder!=null){
                        nodeAdder.accept(a.getName());
                    }
                }
                return null;
            }
            
            private Void visitPluralAttribute(PluralAttributeES a){
                return visitAttribute(a, a::getElementType, nodeAdder, valueSubgrapAdder);
            }
            
            @Override
            public Void visit(SingularAttributeES a, Void arg) {
                return visitAttribute(a, a::getType, nodeAdder, valueSubgrapAdder);
            }

            @Override
            public Void visit(CollectionAttributeES a, Void arg) {
                return visitPluralAttribute(a);
            }

            @Override
            public Void visit(ListAttributeES a, Void arg) {
                return visitPluralAttribute(a);
            }

            @Override
            public Void visit(SetAttributeES a, Void arg) {
                return visitPluralAttribute(a);
            }

            @Override
            public Void visit(MapAttributeES a, Void arg) {
                final TypeES kt = a.getKeyType(), vt = a.getElementType();
                if(!(kt instanceof IdentifiableTypeES || vt instanceof IdentifiableTypeES)){
                  nodeAdder.accept(a.getName());
                  return null;
                } else {
                    visitAttribute(a, ()-> kt, null, keySubgrapAdder);
                    visitAttribute(a, ()-> vt, null, valueSubgrapAdder);
                    return null;
                }
            }
        };
        
        for(AttributeES a : attributes){
            if(!visited.contains(a)){
                visited.add(a);
                a.accept(attributeVisitor, null);
            }
        }
    }
    
    /**
     * Recursively creates an {@link EntityGraph} for the specified {@code rootType} and its transitive associations accessible according to the {@code priviliges} and {@code operation}. Only those attributes accessible according
     * to the {@code priviliges} and {@code operation} are included. The returned graph may be set as the value for the {@code javax.persistence.fetchgraph} property
     * using {@link EntityManager#f}
     * @param rootType the root-type of the query for which to create the {@link EntityGraph}
     * @param priviliges the user's {@link Priviliges} for the instances of the query
     * @param operation the operation performed by the user
     * @param entityManager the {@link EntityManager} to use for creating the returned 
     * @return the creation
     */
    public <T> EntityGraph<T> create(ManagedTypeES<T> rootType, Priviliges priviliges, CRUD operation, EntityManager entityManager) {
        final Set<AttributeES> visited = new HashSet<>();
        final EntityGraph<T> rootGraph = entityManager.createEntityGraph(rootType.getJavaType());
        for (AttributeES a : priviliges.getAttributes(operation, rootType)) {
            visited.add(a);
            populateSubgraph(
                ()-> rootType.getJavaType(), 
                rootGraph::addAttributeNodes, 
                rootGraph::addKeySubgraph, 
                rootGraph::addSubgraph, 
                priviliges, 
                operation, 
                visited
            );
        }
        return rootGraph;
    }
}
