/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.Constructor;

public class DtoManager {
    
    private final Context context;

    private final ScenarioGraph scenarioGraph;

    private final Map<TypeES, Set<AttributeES>> type2readableAttributes = new HashMap<>();

    private Set<AttributeES> getReadableAttributes(ManagedTypeES type) {
        Set<AttributeES> attributes = type2readableAttributes.get(type);
        if (attributes == null) {
            final Vertex vertex = scenarioGraph.getVertex(type);
            if (vertex == null) {
                attributes = Collections.EMPTY_SET;
            } else {
                attributes = vertex.getReadableAttributes(context);
            }
            type2readableAttributes.put(type, attributes);
        }
        return attributes;
    }

    private final TypeVisitor<Object, Object> typeLevelDtoCreator = new TypeVisitor<Object, Object>() {

        private final Map<Object, Object> instance2cachedDto = new HashMap<>();

        private Object getOrCreateDtoFor(ManagedTypeES managedType, Object instance) {
            if (instance == null) {
                return null;
            } else if (instance2cachedDto.containsKey(instance)) {
                return instance2cachedDto.get(instance);
            } else {
                final Set<AttributeES> readableAttributes = getReadableAttributes(managedType);
                if (readableAttributes.isEmpty()) {
                    return null;
                } else {
                    final Object dto = managedType.createInstance();
                    instance2cachedDto.put(instance, dto);
                    for (AttributeES attribute : readableAttributes) {
                        final MemberES javaMember = attribute.getJavaMember();
                        final Object attributeValue = javaMember.get(instance);
                        final Object serializedAttributeValue = attribute.accept(attributeLevelDtoCreator, attributeValue);
                        javaMember.set(dto, serializedAttributeValue);
                    }
                    return dto;
                }
            }
        }
        
        

        @Override
        public Object visit(BasicTypeES t, Object basic) {
            return basic;
        }

        @Override
        public Object visit(EmbeddableTypeES t, Object embeddable) {
            return getOrCreateDtoFor(t, embeddable);
        }

        @Override
        public Object visit(MappedSuperclassTypeES t, Object mapped) {
            return getOrCreateDtoFor(t, mapped);
        }

        @Override
        public Object visit(EntityTypeES t, Object entity) {
            return getOrCreateDtoFor(t, entity);
        }
    };

    private final AttributeVisitor<Object, Object> attributeLevelDtoCreator = new AttributeVisitor<Object, Object>() {

        private <C extends Collection> C visitJavaUtilCollection(
                PluralAttributeES pluralAttribute,
                C collection,
                Constructor<? extends C> retvalConstructor) {
            if (collection == null) {
                return null;
            } else {
                final TypeES elementType = pluralAttribute.getElementType();
                final C retval = retvalConstructor.invoke();
                for (Object element : collection) {
                    final Object serializedElement = elementType.accept(typeLevelDtoCreator, element);
                    retval.add(serializedElement);
                }
                return retval;
            }
        }

        @Override
        public Object visit(SingularAttributeES a, Object arg) {
            return a.getType().accept(typeLevelDtoCreator, arg);
        }

        @Override
        public Object visit(CollectionAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (Collection) arg, ArrayList::new);
        }

        @Override
        public Object visit(ListAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (List) arg, ArrayList::new);
        }

        @Override
        public Object visit(SetAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (Set) arg, HashSet::new);
        }

        @Override
        public Object visit(MapAttributeES a, Object arg) {
            if (arg == null) {
                return arg;
            } else {
                final Map map = (Map) arg, retval = new HashMap();
                final Set<Entry> entrySet = map.entrySet();
                final TypeES keyType = a.getKeyType(), elementType = a.getElementType();
                for (Entry entry : entrySet) {
                    final Object dtoKey = keyType.accept(typeLevelDtoCreator, entry.getKey());
                    final Object dtoValue = elementType.accept(typeLevelDtoCreator, entry.getValue());
                    retval.put(dtoKey, dtoValue);
                }
                return retval;
            }
        }
    };

    public DtoManager(Context context, ScenarioGraph scenarioGraph){
        this.context = context;
        this.scenarioGraph = scenarioGraph;
    }

    protected final Context getContext() {
        return context;
    }

    protected final ScenarioGraph getScenarioGraph() {
        return scenarioGraph;
    }
    
    
    
    /**
     * Gets or creates the dto for the specified {@code instance}. If a dto for the instance had been created allready, said creation is returned 
     * from an internal cache.
     * @param <T>
     * @param instance
     * @return the dto for the specified {@code instance}
     */
    public final <T> T getDtoFor(final T instance){
        if(instance==null){
            return null;
        } else {
            final ManagedTypeES managedType = AbstractMetamodelES.getInstance().managedType(instance.getClass());
            Object dto = managedType.accept(typeLevelDtoCreator, instance);
            return (T) dto;
        }
    }
    
    public final <T> ArrayList<T> getDtosFor(final ManagedTypeES<T> elementType, final List<T> instances){
        ArrayList<T> retval = new ArrayList<>(instances.size());
        for(T instance : instances){
            retval.add(getDtoFor(instance));
        }
        return retval;
    }

}
