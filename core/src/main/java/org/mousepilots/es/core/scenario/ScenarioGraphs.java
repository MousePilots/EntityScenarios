/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.HashMap;
import java.util.Map;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;

/**
 * A singleton factory and registry for {@link ScenarioGraph}s obstained through {@link #getInstance()}
 * 
 */
public final class ScenarioGraphs extends HasSeal{
    
    private static final ScenarioGraphs INSTANCE = new ScenarioGraphs();

    public static ScenarioGraphs getInstance() {
        return INSTANCE;
    }
    
    private final Map<String,ScenarioGraph> registry = new HashMap<>();
    
    private ScenarioGraphs(){}
    
    public final ScenarioGraphBuilder builder(String scenario){
        assertNotSealed();
        return new ScenarioGraphBuilder(scenario);
    }
    
    public final class ScenarioGraphBuilder{
        
        private final ScenarioGraph scenarioGraph;
        
        private ScenarioGraphBuilder(String scenario){
            synchronized(INSTANCE){
                if(get(scenario)!=null){
                    throw new IllegalStateException("duplicate scenario " + scenario);
                }
                this.scenarioGraph = new ScenarioGraph(scenario);
                registry.put(scenario, scenarioGraph);
            }
        }
        
        public ScenarioGraphBuilder addAssociation(AssociationES association, boolean addInverse){
            scenarioGraph.getOrCreate(association);
            if(addInverse){
                final AssociationES inverse = association.getInverse();
                if(inverse!=null){
                    scenarioGraph.getOrCreate(inverse);
                }
            }
            return this;
        }
        
        public VertexBuilder addVertex(ManagedTypeES managedTypeES){
            return new VertexBuilder(managedTypeES);
        }
    
        public ScenarioGraph build(){
            scenarioGraph.seal();
            return scenarioGraph;
        }
        
        public final class VertexBuilder{
            private final Vertex vertex;

            private VertexBuilder(ManagedTypeES managedType){
                this.vertex = scenarioGraph.getOrCreate(managedType);
            }
            
            protected abstract class AbstractAuthorizationBuilder extends AuthorizationBuilder<VertexBuilder>{

                @Override
                protected final Vertex getVertex() {
                    return VertexBuilder.this.vertex;
                }
                
            }
            
            public class TypeAuthorizationBuilder extends AbstractAuthorizationBuilder{

                @Override
                public VertexBuilder build() {
                    vertex.addTypeLevelAuthorization(super.buildAuthorization());
                    return VertexBuilder.this;
                }

            }
            
            public class AttributeAuthorizationBuilder extends AbstractAuthorizationBuilder{

                private final AttributeES attribute;

                private AttributeAuthorizationBuilder(AttributeES attribute) {
                    this.attribute = attribute;
                }
                
                @Override
                public VertexBuilder build() {
                    vertex.addAttributeLevelAuthorization(attribute, super.buildAuthorization());
                    return VertexBuilder.this;
                }
                
            }
            
            /**
             * @return {@code this}' {@link ScenarioGraphBuilder}
             */
            public ScenarioGraphBuilder build(){
                return ScenarioGraphBuilder.this;
            }
        }
    }
    
    public ScenarioGraph get(String scenario){
        return registry.get(scenario);
    }
    
}
