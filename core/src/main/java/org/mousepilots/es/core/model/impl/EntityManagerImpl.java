/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.util.HashMap;
import java.util.Map;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEmbeddable;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.command.DeleteEntity;
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

/**
 *
 * @author jgeenen
 */
@Framework
public class EntityManagerImpl implements EntityManagerES{
    
    private final EntityManagerFactoryImpl entityManagerFactory;

    private final Map<Class, Map<Object, Proxy>> entityClass2id2entity = new HashMap<>();

    private final EntityTransactionImpl entityTransaction = new EntityTransactionImpl();

    protected EntityManagerImpl(EntityManagerFactoryImpl entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public <T, ID, C extends Command> void manage(Proxy<T> proxy){
        final ProxyAspect<T> proxyAspect = proxy.__getProxyAspect();
        proxyAspect.setEntityManager(this);
        final ManagedTypeES<T> type = proxyAspect.getType();
        if(type instanceof EntityTypeESImpl){
            final EntityTypeESImpl entityType = (EntityTypeESImpl) type;
            final Object id = entityType.getId().getJavaMember().get(proxy.__subject());
            final Map<Object, Proxy> id2entity = Maps.getOrCreate(
                    entityClass2id2entity,
                    entityType.getJavaType(),
                    HashMap::new);
            id2entity.put(id, proxy);
        }
        proxyAspect.setManagedMode(true);
    }

    public <T, ID, C extends Command> void unManage(Proxy<T> proxy) {
        final ProxyAspect<T> proxyAspect = proxy.__getProxyAspect();
        proxyAspect.setManagedMode(false);
        final ManagedTypeES<T> type = proxyAspect.getType();
        if(type instanceof EntityTypeESImpl){
            final EntityTypeESImpl entityType = (EntityTypeESImpl) type;
            final Object id = entityType.getId().getJavaMember().get(proxy.__subject());
            final Class<T> javaType = type.getJavaType();
            final Map<Object, Proxy> id2entity = entityClass2id2entity.get(javaType);
            if(id2entity.containsKey(id)){
                if(id2entity.remove(id)==proxy){
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
                    final EntityTypeES entityTypeES = (EntityTypeES) type;
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
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return (T) Maps.get(entityClass2id2entity, entityClass, primaryKey);
    }


    @Override
    public boolean contains(Object entity) {
        if (entity == null || !(entity instanceof Proxy) ) {
            return false;
        } else {
            Proxy proxy = (Proxy) entity;
            return proxy.__getProxyAspect().getEntityManager()==this;
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
