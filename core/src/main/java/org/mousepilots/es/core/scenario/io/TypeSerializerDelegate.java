/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.scenario.ScenarioGraph;
import org.mousepilots.es.core.scenario.Vertex;
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author jgeenen
 * @param <S>
 */
public class TypeSerializerDelegate<S extends Serializer> extends SerializerDelegate<S> implements TypeVisitor<Object,Object>{
    
    private final Map<IdentifiableTypeES,Map<Object,Object>> type2Id2Instance = new HashMap<>();

    protected TypeSerializerDelegate(S serializer) {
        super(serializer);
    }

    /**
     * Gets the previously serialized value of {@code instance} if existent, otherwise {@code null}
     * @param identifiableType the {@code instance}'s {@link IdentifiableTypeES}
     * @param instance an instance of {@code identifiableType.getJavaType()}
     * @return the previously serialized value of {@code instance} if existent, otherwise {@code null}
     */
    protected Object getPreSerializedValue(IdentifiableTypeES identifiableType, Object instance){
        if(instance==null){
            return null;
        } else {
            final SingularAttributeES idAttribute = identifiableType.getId(identifiableType.getIdType().getJavaType());
            final Map<Object, Object> id2Instance = type2Id2Instance.get(identifiableType);
            if(id2Instance==null){
                return null;
            } else {
                final Object idValue = idAttribute.getJavaMember().get(instance);
                return id2Instance.get(idValue);
            }
        }
    }
    
    /**
     * Marks the {@code serializedInstance} of {@code identifiableType} with {@code id} as serialized. This preserves
     * the {@code ==} relation between managed types and provides a recursion limit in the serialized object graph.
     * @param identifiableType
     * @param id
     * @param serializedInstance 
     */
    protected void markSerialized(IdentifiableTypeES identifiableType, Object id, Object serializedInstance){
        final Map<Object, Object> id2Instance = Maps.getOrCreate(type2Id2Instance, identifiableType, HashMap::new);
        id2Instance.put(id, serializedInstance);
    }
    
    protected void copyAttributes(final Set<AttributeES> attributes, final Object source, final Object target){
        final ScenarioGraph scenarioGraph = getParent().getScenarioGraph();
        final Vertex vertex = scenarioGraph.getVertex(source.getClass());
        final AttributeSerializerDelegate attributeSerializer = getParent().getAttributeSerializerDelegate();
        for(AttributeES attribute : attributes){
            if(vertex.isAllowed(attribute, CRUD.READ)){
                final MemberES javaMember = attribute.getJavaMember();
                final Object attributeValue = javaMember.get(source);
                final Object serializedAttributeValue = attribute.accept(attributeSerializer,attributeValue);
                javaMember.set(target, serializedAttributeValue);
            }
        }
    }

    protected Object visitIdentifiable(IdentifiableTypeES t, Object identifiable) {
        Object serializedIdentifiable = getPreSerializedValue(t, identifiable);
        if(serializedIdentifiable==null){
            serializedIdentifiable = t.createInstance();
            final SingularAttributeES idAttribute = t.getId(t.getIdType().getJavaType());
            final MemberES idMember = idAttribute.getJavaMember();
            final Object idValue = idMember.get(identifiable);
            idMember.set(serializedIdentifiable, idValue);
            markSerialized(t, idValue, serializedIdentifiable);
            final Set<AttributeES> attributes = new HashSet<>(t.getAttributes());
            attributes.remove(idAttribute);
            copyAttributes(attributes, identifiable, serializedIdentifiable);
        }
        return serializedIdentifiable;
    }

    @Override
    public Object visit(BasicTypeES t, Object basicTypeInstance) {
        return basicTypeInstance;
    }


    @Override
    public Object visit(EmbeddableTypeES t, Object embeddable) {
        final Set<AttributeES> attributes = t.getAttributes();
        final Object serializedEmbeddable = t.createInstance();
        copyAttributes(attributes, embeddable, serializedEmbeddable);
        return serializedEmbeddable;
    }
    

    @Override
    public Object visit(MappedSuperclassTypeES t, Object mappedSuperClass) {
        return visitIdentifiable(t,mappedSuperClass);
    }
    
    @Override
    public Object visit(EntityTypeES t, Object entity) {
        return visitIdentifiable(t,entity);
    }


}
