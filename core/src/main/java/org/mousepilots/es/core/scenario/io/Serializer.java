/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.io;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.core.scenario.ScenarioGraph;
import org.mousepilots.es.core.scenario.Vertex;

/**
 * <strong>NB:</strong> Instances are NOT thread-safe and MUST be disposed of after 
 * each call to {@link #serialize(java.util.List)}.
 * 
 * @author jgeenen
 */
public abstract class Serializer {
    
    private final Context context;

    private final ScenarioGraph scenarioGraph;

    public Serializer(Context context, ScenarioGraph scenarioGraph){
        this.context = context;
        this.scenarioGraph = scenarioGraph;
    }

    /**
     * @return {@code this}' {@link ScenarioGraph}
     */
    protected final ScenarioGraph getScenarioGraph() {
        return scenarioGraph;
    }

    protected final Context getContext() {
        return context;
    }
    
    protected abstract AttributeSerializerDelegate getAttributeSerializerDelegate();
    
    protected abstract TypeSerializerDelegate getTypeSerializerDelegate();
    
    /**
     * Serializes the {@code value}: an instance of some {@link ManagedTypeES#getJavaType()}
     * @param value an instance of some {@link ManagedType#getJavaType()}
     * @return if {@link CRUD#READ} is allowed for the {@code value}'s {@link ManagedType} in the {@link #scenarioGraph}: the serialized {@code value}, otherwise {@code null}
     */
    protected Object serializeManagedTypeValue(Object value){
        if(value==null){
            return null;
        } else {
            final Vertex vertex = getScenarioGraph().getVertex(value.getClass());
            if(vertex==null){
                return null;
            } else {
                if(vertex.isAllowedOnType(CRUD.READ,context)){
                    final TypeSerializerDelegate typeSerializerDelegate = getTypeSerializerDelegate();
                    return vertex.getType().accept(typeSerializerDelegate,value);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Serializes the {@code value}: an instance of the {@link AttributeES#getJavaType()}
     * @param typeVertex the {@link Vertex} containing the {@code attribute}
     * @param attribute the {@link AttributeES} corresponding to the {@code value}
     * @param value an instance of some {@link AttributeES#getJavaType()}
     * @return if {@link CRUD#READ} is allowed for the {@code value}'s {@code attribute} in the {@link #scenarioGraph}: the serialized {@code value}, otherwise {@code null}
     */
    protected Object serializeAttributeValue(Vertex typeVertex, AttributeES attribute, Object value){
        if(value!=null && typeVertex.isAllowedOnAttribute(attribute, CRUD.READ, context)){
            final AttributeSerializerDelegate attributeSerializerDelegate = getAttributeSerializerDelegate();
            return attribute.accept(attributeSerializerDelegate,value);
        } else {
            return null;
        }
        
    }

    /**
     * Serializes the {@code values} according to
     * {@link CRUD#READ}-rights in the {@link #scenarioGraph}.
     * @param values MUST be managed by an {@link EntityManager} in order to allow lazy loading of attributes
     * @return the serialized {@code values}
     */
    public List serialize(List values) {
        final List retval = new ArrayList<>(values.size());
        for (Object serializable : values){
            final Object serializedValue = serializeManagedTypeValue(serializable);
            retval.add(serializedValue);
        }
        return retval;
    }

}
