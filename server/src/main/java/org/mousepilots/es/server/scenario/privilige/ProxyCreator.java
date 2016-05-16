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
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.proxy.Proxy;

public class ProxyCreator {

    private final Priviliges priviliges;

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
                        instance2Proxy.put(instance, proxy);
                        for (AttributeES attribute : readableAttributes) {
                            final MemberES javaMember = attribute.getJavaMember();
                            final Object instanceAttributeValue = javaMember.get(instance);
                            final Object proxyAttributeValue = attribute.accept(attributeLevelDtoCreator, instanceAttributeValue);
                            javaMember.set(proxy, proxyAttributeValue);
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
                final TypeES elementType = pluralAttribute.getElementType();
                final C retval = (C) pluralAttribute.createEmpty();
                for (Object element : collection) {
                    final Object serializedElement = elementType.accept(typeLevelProxyCreator, element);
                    retval.add(serializedElement);
                }
                return retval;
            }
        }

        @Override
        public Object visit(SingularAttributeES a, Object arg) {
            return a.getType().accept(typeLevelProxyCreator, arg);
        }

        @Override
        public Object visit(CollectionAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (Collection) arg);
        }

        @Override
        public Object visit(ListAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (List) arg);
        }

        @Override
        public Object visit(SetAttributeES a, Object arg) {
            return visitJavaUtilCollection(a, (Set) arg);
        }

        @Override
        public Object visit(MapAttributeES a, Object arg) {
            if (arg == null) {
                return arg;
            } else {
                final Map map = (Map) arg, retval = a.createEmpty();
                final Set<Entry> entrySet = map.entrySet();
                final TypeES keyType = a.getKeyType(), elementType = a.getElementType();
                for (Entry entry : entrySet) {
                    final Object key   = keyType.accept(typeLevelProxyCreator, entry.getKey());
                    final Object value = elementType.accept(typeLevelProxyCreator, entry.getValue());
                    retval.put(key, value);
                }
                return retval;
            }
        }
    };

    public ProxyCreator(Priviliges priviliges) {
        this.priviliges = priviliges;
    }

    public final <T> Proxy<T> getProxyFor(final ManagedTypeES<T> elementType, final T instance) {
        if (instance == null) {
            return null;
        } else {
            final ManagedTypeES managedType = AbstractMetamodelES.getInstance().managedType(instance.getClass());
            final Proxy<T> dto = (Proxy<T>) managedType.accept(typeLevelProxyCreator, instance);
            return dto;
        }
    }

    public final <T> ArrayList<Proxy<T>> getProxiesFor(final ManagedTypeES<T> elementType, final List<T> instances) {
        ArrayList<Proxy<T>> retval = new ArrayList<>(instances.size());
        for (T instance : instances) {
            retval.add(getProxyFor(elementType, instance));
        }
        return retval;
    }

}
