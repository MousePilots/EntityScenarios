/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import org.mousepilots.es.core.scenario.priviliges.Priviliges;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
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
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.proxy.Proxy;

public class ProxyCreator {

    private final Priviliges priviliges;
    
    private static final MetamodelES METAMODEL = AbstractMetamodelES.getInstance();

    private final TypeVisitor<Object, Object> typeLevelProxyCreator = new TypeVisitor<Object, Object>() {

        private final Map<Object, Proxy> instance2Proxy = new HashMap<>();

        private <T> Proxy<T> getOrCreateProxy(ManagedTypeES<T> managedType, T instance) {
            if (instance == null) {
                return null;
            } else {
                if (instance2Proxy.containsKey(instance)) {
                    return instance2Proxy.get(instance);
                } else {
                    final Set<AttributeES> readableAttributes = priviliges.getAttributes(CRUD.READ, managedType);
                    if (readableAttributes.isEmpty()) {
                        return null;
                    } else {
                        final Proxy<T> proxy = managedType.createProxy();
                        proxy.__getProxyAspect().setManagedMode(false);
                        instance2Proxy.put(instance, proxy);
                        for (AttributeES attribute : readableAttributes) {
                            if(!attribute.isReadOnly()){
                                final MemberES javaMember = attribute.getJavaMember();
                                final Object instanceAttributeValue = javaMember.get(instance);
                                final Object proxyAttributeValue = attribute.accept(attributeLevelDtoCreator, instanceAttributeValue);
                                javaMember.set(proxy, proxyAttributeValue);
                            }
                        }
                        return proxy;
                    }      
                }
            }
        }

        @Override
        public Object visit(BasicTypeES t, Object basicValue) {
            return basicValue;
        }

        @Override
        public Object visit(EmbeddableTypeES t, Object embeddable) {
            return getOrCreateProxy(t, embeddable);
        }

        @Override
        public Object visit(MappedSuperclassTypeES t, Object mappedSuperclass) {
            return getOrCreateProxy(t, mappedSuperclass);
        }

        @Override
        public Object visit(EntityTypeES t, Object entity) {
            return getOrCreateProxy(t, entity);
        }
    };

    private final AttributeVisitor<Object, Object> attributeLevelDtoCreator = new AttributeVisitor<Object, Object>() {

        private <C extends Collection> C visitJavaUtilCollection(PluralAttributeES pluralAttribute,C collection) {
            if (collection == null) {
                return null;
            } else {
                final C retval = (C) pluralAttribute.createEmpty();
                for (Object element : collection) {
                    retval.add(serialize(element));
                }
                return retval;
            }
        }

        @Override
        public Object visit(SingularAttributeES a, Object attributeValue) {
            return serialize(attributeValue);
        }

        @Override
        public Object visit(CollectionAttributeES a, Object attributeValue) {
            return visitJavaUtilCollection(a, (Collection) attributeValue);
        }

        @Override
        public Object visit(ListAttributeES a, Object attributeValue) {
            return visitJavaUtilCollection(a, (List) attributeValue);
        }

        @Override
        public Object visit(SetAttributeES a, Object attributeValue) {
            return visitJavaUtilCollection(a, (Set) attributeValue);
        }

        @Override
        public Object visit(MapAttributeES a, Object attributeValue) {
            if (attributeValue == null) {
                return attributeValue;
            } else {
                final Map map = (Map) attributeValue, retval = a.createEmpty();
                final Set<Entry> entrySet = map.entrySet();
                for (Entry entry : entrySet) {
                    final Object key   = serialize(entry.getKey());
                    final Object value = serialize(entry.getValue());
                    retval.put(key, value);
                }
                return retval;
            }
        }
    };

    public ProxyCreator(Priviliges priviliges) {
        this.priviliges = priviliges;
    }
    
    public final <T> T serialize(T value){
        if(value==null){
            return null;
        } else {
            final TypeES<T> type = (TypeES<T>) METAMODEL.type(value.getClass());
            return (T) type.accept(typeLevelProxyCreator, value);
        }
    }

    public final <T> Proxy<T> getProxyFor(final T instance) {
        if (instance == null) {
            return null;
        } else {
            final ManagedTypeES managedType = METAMODEL.managedType(instance.getClass());
            final Proxy<T> dto = (Proxy<T>) managedType.accept(typeLevelProxyCreator, instance);
            return dto;
        }
    }

    public final <T> ArrayList<Proxy<T>> getProxiesFor(final List<T> instances) {
        ArrayList<Proxy<T>> retval = new ArrayList<>(instances.size());
        for (T instance : instances) {
            retval.add(getProxyFor(instance));
        }
        return retval;
    }

}
