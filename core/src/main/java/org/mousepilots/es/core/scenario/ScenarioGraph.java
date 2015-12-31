/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.ScenarioGraph.Arc;
import org.mousepilots.es.core.scenario.ScenarioGraph.Vertex;
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author jgeenen
 */
public class ScenarioGraph implements DirectedGraph<Vertex,Arc>{
    /**
     * A globally unique identifier for {@code this}
     */
    public  final String scenario;
    private final Map<Class,Vertex>         class2Vertex= new HashMap<>();
    private final Map<ManagedTypeES,Vertex> type2Vertex = new HashMap<>();
    private final Map<AttributeES,EnumMap<AssociationTypeES,Arc>> attribute2AssociationType2Arc = new HashMap<>();

    private ScenarioGraph(String scenario) {
        this.scenario = scenario;
    }

    public abstract class Element{
        public ScenarioGraph getScenarioGraph(){
            return ScenarioGraph.this;
        }
    }
    
    /**
     * Represents a {@link ManagedTypeES} within a {@link ScenarioGraph}
     */
    public final class Vertex extends Element{
        
        private final Set<Arc> in = new HashSet<>(), out = new HashSet<>();
        private final Set<Vertex> pred = new HashSet<>(), succ = new HashSet<>();
        private final ManagedTypeES type;

        private Vertex(ManagedTypeES managedType) {
            this.type = managedType;
        }

        /**
         * Gets (or creates if absent) the arc {@code this} &rarr the association's target {@link Vertex}
         * @param attribute
         * @param associationType
         * @return 
         */
        Arc getOrCreate(AttributeES attribute,AssociationTypeES associationType){
            final AssociationES association = attribute.getAssociation(associationType);
            if(association==null){
                throw new IllegalArgumentException(attribute + " is no association");
            } else {
                final EnumMap<AssociationTypeES, Arc> associationType2Arc = Maps.getOrCreate(
                        attribute2AssociationType2Arc, 
                        attribute, 
                        () -> {return new EnumMap<>(AssociationTypeES.class);}
                );
                Arc retval = associationType2Arc.get(associationType);
                if(retval==null){
                    final AssociationTargetTypeResolver associationTargetTypeResolver = new AssociationTargetTypeResolver(associationType);
                    attribute.accept(associationTargetTypeResolver);
                    final ManagedTypeES targetType= associationTargetTypeResolver.getAssociationTargetType();
                    final Vertex targetVertex = getScenarioGraph().getOrCreate(targetType);
                    retval = new Arc(association,this, targetVertex);
                    associationType2Arc.put(associationType, retval);
                    this.succ.add(targetVertex);
                    targetVertex.pred.add(this);
                    
                }
                return retval;
            }
        }
        
        public ManagedTypeES getType() {
            return type;
        }

        public Set<Arc> getIn() {
            return Collections.unmodifiableSet(in);
        }

        public Set<Arc> getOut() {
            return Collections.unmodifiableSet(out);
        }
        
        
        
        
        

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.type) + Objects.hashCode(this.getScenarioGraph()) ;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Vertex other = (Vertex) obj;
            return Objects.equals(this.type, other.type) && Objects.equals(this.getScenarioGraph(), other.getScenarioGraph());
        }
        
        
    }
    
    public final class Arc extends Element{
        
        private final AssociationES association;
        private final Vertex source, target;

        private Arc(AssociationES association,Vertex source, Vertex target) {
            this.association = association;
            this.source = source;
            this.target = target;
        }

        public AssociationES getAssociation() {
            return association;
        }
        
        public Vertex getSource() {
            return source;
        }

        public Vertex getTarget() {
            return target;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.association) + Objects.hashCode(getScenarioGraph());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Arc other = (Arc) obj;
            return  Objects.equals(this.association, other.association) &&
                    Objects.equals(this.getScenarioGraph(), other.getScenarioGraph());
        }
        
    }
    
    Vertex getOrCreate(ManagedTypeES managedType){
        Vertex vertex = type2Vertex.get(managedType);
        if(vertex==null){
            vertex= new Vertex(managedType);
            class2Vertex.put(managedType.getJavaType(), vertex);
            type2Vertex.put(managedType, vertex);
        }
        return vertex;
    }

    @Override
    public int inDegreeOf(Vertex vertex) {
        return vertex.getIn().size();
    }

    @Override
    public Set<Arc> incomingEdgesOf(Vertex vertex) {
        return vertex.getIn();
    }

    @Override
    public int outDegreeOf(Vertex vertex) {
        return vertex.getOut().size();
    }

    @Override
    public Set<Arc> outgoingEdgesOf(Vertex vertex) {
        return vertex.getOut();
    }

    @Override
    public Set<Arc> getAllEdges(Vertex sourceVertex, Vertex targetVertex) {
        if(sourceVertex==null || targetVertex==null){
            return Collections.EMPTY_SET;
        }
        Set<Arc> retval = new HashSet<>();
        for(Arc a : sourceVertex.getOut()){
            if(a.getTarget().equals(targetVertex)){
                retval.add(a);
            }
        }
        return retval;
    }

    @Override
    public Arc getEdge(Vertex sourceVertex, Vertex targetVertex) {
        for(Arc a : sourceVertex.getOut()){
            if(a.getTarget().equals(targetVertex)){
                return a;
            }
        }
        return null;
    }

    @Override
    public EdgeFactory<Vertex, Arc> getEdgeFactory() {
        return (Vertex sourceVertex, Vertex targetVertex) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

    }

    @Override
    public Arc addEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addEdge(Vertex sourceVertex, Vertex targetVertex, Arc e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addVertex(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsEdge(Arc e) {
        return e.getScenarioGraph()==this;
    }

    @Override
    public boolean containsVertex(Vertex v) {
        return v.getScenarioGraph()==this;
    }

    @Override
    public Set<Arc> edgeSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Arc> edgesOf(Vertex vertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAllEdges(Collection<? extends Arc> edges) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Arc> removeAllEdges(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAllVertices(Collection<? extends Vertex> vertices) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Arc removeEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeEdge(Arc e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeVertex(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Vertex> vertexSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vertex getEdgeSource(Arc e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vertex getEdgeTarget(Arc e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getEdgeWeight(Arc e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.scenario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScenarioGraph other = (ScenarioGraph) obj;
        return Objects.equals(this.scenario, other.scenario);
    }
    
}
