/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import org.mousepilots.es.core.model.proxy.ProxyAspect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.change.impl.Create;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.Generator;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.model.MetamodelES;

/**
 *
 * @author jgeenen
 */
public class EntityManagerESImpl implements EntityManager {

     private final MetamodelES metaModel;

     private final Map<Class, Map<Object, Object>> entityClass2id2entity = new HashMap<>();

     protected EntityManagerESImpl(MetamodelES metaModel) {
          this.metaModel = metaModel;
     }

     @Override
     public final <E> E create(ManagedTypeES<E> type, Object id) {
          final Proxy<E> proxy = type.createProxy();
          final ProxyAspect proxyAspect = proxy.__getProxyAspect();
          proxyAspect.setEntityManager(this);
          if (type instanceof IdentifiableTypeES) {
               final IdentifiableTypeES identifiableType = (IdentifiableTypeES) type;
               final SingularAttributeES idAttribute = IdentifiableTypeUtils.getIdAttribute(identifiableType);
               idAttribute.getJavaMember().set(proxy, idAttribute.isGenerated());
               final Object actualId;
               if (idAttribute.isGenerated()) {
                    final Generator generator = idAttribute.getGenerator();
                    actualId = generator.generate();
                    idAttribute.getJavaMember().set(proxy, actualId);
               } else {
                    if (id == null) {
                         throw new IllegalArgumentException("id must not be null for " + type);
                    } else {
                         actualId = id;
                    }
               }
               idAttribute.getJavaMember().set(proxy, actualId);
               if (proxyAspect.isManagedMode()) {
                    //todo verhuizen naar Impl
                    Change change = new Create(idAttribute.wrapForChange(id), identifiableType);
               }
          }
          return (E) proxy;
     }

     @Override
     public void persist(Object entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> T merge(T entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void remove(Object entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> T find(Class<T> entityClass, Object primaryKey) {
          return (T) Maps.get(entityClass2id2entity, entityClass, primaryKey);
     }

     @Override
     public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
          return find(entityClass, primaryKey);
     }

     @Override
     public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
          return find(entityClass, primaryKey);
     }

     @Override
     public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
          return find(entityClass, primaryKey);
     }

     @Override
     public <T> T getReference(Class<T> entityClass, Object primaryKey) {
          return find(entityClass, primaryKey);
     }

     @Override
     public void flush() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void setFlushMode(FlushModeType flushMode) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public FlushModeType getFlushMode() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void lock(Object entity, LockModeType lockMode) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void refresh(Object entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void refresh(Object entity, Map<String, Object> properties) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void refresh(Object entity, LockModeType lockMode) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void clear() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void detach(Object entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public boolean contains(Object entity) {
          if (entity == null) {
               return false;
          } else {
               final Class<? extends Object> entityClass = entity.getClass();
               final ManagedTypeES<? extends Object> managedType = metaModel.managedType(entityClass);
               if (managedType instanceof EntityTypeES) {
                    EntityTypeES entityType = (EntityTypeES) managedType;
                    final SingularAttributeES idAttribute = entityType.getId(entityType.getIdType().getJavaType());
                    final Object id = idAttribute.getJavaMember().get(entity);
                    return Maps.get(entityClass2id2entity, entityClass, id) != null;
               } else {
                    throw new IllegalArgumentException("bad type: " + managedType);
               }
          }

     }

     @Override
     public LockModeType getLockMode(Object entity) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void setProperty(String propertyName, Object value) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Map<String, Object> getProperties() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createQuery(String qlString) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createQuery(CriteriaUpdate updateQuery) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createQuery(CriteriaDelete deleteQuery) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createNamedQuery(String name) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createNativeQuery(String sqlString) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createNativeQuery(String sqlString, Class resultClass) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Query createNativeQuery(String sqlString, String resultSetMapping) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void joinTransaction() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public boolean isJoinedToTransaction() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> T unwrap(Class<T> cls) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public Object getDelegate() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public void close() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public boolean isOpen() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public EntityTransaction getTransaction() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public EntityManagerFactory getEntityManagerFactory() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public CriteriaBuilder getCriteriaBuilder() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public MetamodelES getMetamodel() {
          return metaModel;
     }

     @Override
     public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public EntityGraph<?> createEntityGraph(String graphName) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public EntityGraph<?> getEntityGraph(String graphName) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

}
