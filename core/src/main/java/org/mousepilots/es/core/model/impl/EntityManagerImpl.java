/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Predicate;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEmbeddable;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.command.DeleteEntity;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.util.Framework;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;

/**
 *
 * @author jgeenen
 */
@Framework
public class EntityManagerImpl implements EntityManagerES {

    private final EntityManagerFactoryImpl entityManagerFactory;

    private final Map<Class, Map<Object, Proxy>> entityClass2id2entity = new HashMap<>();

    private final EntityTransactionImpl entityTransaction = new EntityTransactionImpl();

    protected EntityManagerImpl(EntityManagerFactoryImpl entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private final AttributeVisitor<Void, Proxy> attributeManager = new AttributeVisitor<Void, Proxy>() {

        private Void visitJavaUtilCollection(PluralAttributeES pluralAttribute, Proxy owner) {
            if (pluralAttribute.getElementType() instanceof ManagedTypeES) {
                final Collection<Proxy> attributeValue = (Collection<Proxy>) pluralAttribute.getJavaMember().get(owner);
                if(attributeValue!=null){
                    manageAll(attributeValue);
                }
            }
            return null;
        }

        @Override
        public Void visit(SingularAttributeES a, Proxy owner) {
            if (a.getType() instanceof ManagedTypeES) {
                final Proxy attributeValue = (Proxy) a.getJavaMember().get(owner);
                if(attributeValue!=null){
                    manage(attributeValue);
                }
            }
            return null;
        }


        @Override
        public Void visit(CollectionAttributeES a, Proxy owner) {
            return visitJavaUtilCollection(a, owner);
        }

        @Override
        public Void visit(ListAttributeES a, Proxy owner) {
            return visitJavaUtilCollection(a, owner);
        }

        @Override
        public Void visit(SetAttributeES a, Proxy owner) {
            return visitJavaUtilCollection(a, owner);
        }

        @Override
        public Void visit(MapAttributeES a, Proxy owner) {
            final Map attributeValue = (Map) a.getJavaMember().get(owner);
            if (attributeValue != null) {
                if (a.getKeyType() instanceof ManagedTypeES) {
                    manageAll(attributeValue.keySet());
                }
                if (a.getElementType() instanceof ManagedTypeES) {
                    manageAll(attributeValue.values());
                }

            }
            return null;
        }

    };

    public void manage(Proxy proxy) {
        final ProxyAspect proxyAspect = proxy.__getProxyAspect();
        if (proxyAspect.getEntityManager() != this) {
            proxyAspect.setEntityManager(this);
            final ManagedTypeES type = proxyAspect.getType();
            if (type instanceof EntityTypeESImpl) {
                final EntityTypeESImpl entityType = (EntityTypeESImpl) type;
                final Object id = entityType.getId().getJavaMember().get(proxy.__subject());
                final Map<Object, Proxy> id2entity = Maps.getOrCreate(
                        entityClass2id2entity,
                        entityType.getJavaType(),
                        HashMap::new);
                id2entity.put(id, proxy);
            }
            final Set<AttributeES> attributes = (Set) type.getAttributes();
            for (AttributeES attribute : attributes) {
                attribute.accept(attributeManager, proxy);
            }
            proxyAspect.setManagedMode(true);
        }
    }

    public void manageAll(final Collection<Proxy> attributeValue) {
        for (Proxy element : attributeValue) {
            manage(element);
        }
    }

    public <T, ID, C extends Command> void unManage(Proxy<T> proxy) {
        final ProxyAspect<T> proxyAspect = proxy.__getProxyAspect();
        proxyAspect.setManagedMode(false);
        final ManagedTypeES<T> type = proxyAspect.getType();
        if (type instanceof EntityTypeESImpl) {
            final EntityTypeESImpl entityType = (EntityTypeESImpl) type;
            final Object id = entityType.getId().getJavaMember().get(proxy.__subject());
            final Class<T> javaType = type.getJavaType();
            final Map<Object, Proxy> id2entity = entityClass2id2entity.get(javaType);
            if (id2entity.containsKey(id)) {
                if (id2entity.remove(id) == proxy) {
                    if (id2entity.isEmpty()) {
                        entityClass2id2entity.remove(javaType);
                    }
                } else {
                    throw new IllegalArgumentException("another proxy for " + javaType + " with id " + id + " was registered by " + this);
                }
            } else {
                throw new IllegalArgumentException("entity of " + javaType + " with id " + id + " is registered by " + this);
            }
        }
        proxyAspect.setEntityManager(null);
    }

    @Override
    public final <E, ID> E create(EntityTypeES<E> type, ID id) {
        final CreateEntity<E, ID> createEntity = new CreateEntity<>(this, type, id);
        createEntity.executeOnClient();
        final Proxy<E> proxy = createEntity.getProxy();
        return proxy.__subject();
    }

    @Override
    public <E> E create(EntityTypeES<E> type) {
        return create(type, null);
    }

    @Override
    public <E> E create(EmbeddableTypeES<E> type) {
        final CreateEmbeddable<E> createEmbeddable = new CreateEmbeddable(this, type);
        createEmbeddable.executeOnClient();
        return createEmbeddable.getProxy().__subject();
    }

    @Override
    public final void remove(Object entity) {
        if (entity != null && entity instanceof Proxy) {
            Proxy proxy = (Proxy) entity;
            final ProxyAspect proxyAspect = proxy.__getProxyAspect();
            if (proxyAspect.getEntityManager() == this) {
                final ManagedTypeESImpl type = proxy.__getProxyAspect().getType();
                if (type instanceof EntityTypeES) {
                    if (proxyAspect.isDeleted()) {
                        throw new IllegalArgumentException(entity + " was removed allready");
                    }
                    final DeleteEntity deleteEntity = new DeleteEntity(proxy);
                    deleteEntity.executeOnClient();
                } else {
                    throw new IllegalArgumentException(entity + " is no entity");
                }
            } else {
                throw new IllegalArgumentException(entity + " is no proxy managed by " + this);
            }
        } else {
            throw new IllegalArgumentException(entity + " is no proxy managed by " + this);
        }
    }

    
    
    @Override
    public <T> T find(Class<T> identifiableJavaType, Object primaryKey){
        final IdentifiableTypeES<T> identifiableType = (IdentifiableTypeES<T>) AbstractMetamodelES.getInstance().type(identifiableJavaType);
        return find(identifiableType, primaryKey);
    }

    @Override
    public <T> T find(final IdentifiableTypeES<T> identifiableType, Object primaryKey) {
        final SortedSet<IdentifiableTypeES<? extends T>> subTypes = (SortedSet) identifiableType.getSubTypes();
        for(IdentifiableTypeES subType : subTypes){
            final Object identifiable = Maps.get(entityClass2id2entity, subType.getJavaType(), primaryKey);
            if(identifiable!=null){
                return (T) identifiable;
            }
        }
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> identifiableJavaClass, Predicate<T> restrictions, Comparator<T> sorter){
        final IdentifiableTypeES<T> identifiableType = (IdentifiableTypeES<T>) AbstractMetamodelES.getInstance().type(identifiableJavaClass);
        return select(identifiableType, restrictions, sorter);
    }
    
    @Override
    public <T> List<T> select(IdentifiableTypeES<T> identifiableType, Predicate<T> restrictions, Comparator<T> sorter) {
        final Set<T> candidates = new HashSet<>();
        final SortedSet<IdentifiableTypeES<? extends T>> subTypes = (SortedSet) identifiableType.getSubTypes();
        for(IdentifiableTypeES<? extends T> subType : subTypes){
            final Map<Object, T> id2Identifiable = (Map) entityClass2id2entity.get(subType.getJavaType());
            if(id2Identifiable!=null){
                candidates.addAll(id2Identifiable.values());
            }
        }
        final LinkedList<T> resultList = new LinkedList<>();
        if(restrictions==null){
            resultList.addAll(candidates);
        } else {
            for(T candidate : candidates){
                if(restrictions.test(candidate)){
                    resultList.add(candidate);
                }
            }
        }
        if(sorter!=null){
            Collections.sort(resultList, sorter);
        }
        return resultList;
    }
    
    

    @Override
    public boolean contains(Object entity) {
        if (entity == null || !(entity instanceof Proxy)) {
            return false;
        } else {
            Proxy proxy = (Proxy) entity;
            return proxy.__getProxyAspect().getEntityManager() == this;
        }
    }

    @Override
    public EntityTransactionImpl getTransaction() {
        return entityTransaction;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public MetamodelES getMetamodel() {
        return entityManagerFactory.getMetamodel();
    }
}
