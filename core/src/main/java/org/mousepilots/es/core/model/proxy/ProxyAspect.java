/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.impl.container.Container;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerESImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.impl.PluralAttributeESImpl;

/**
 *
 * @author jgeenen
 */
public final class ProxyAspect implements Serializable {

     private static final AtomicLong CREATION_ID_GENERATOR = new AtomicLong(0L);

     private static final Set<CRUD> INITIAL_CLIENT_OPS = Collections.unmodifiableSet(EnumSet.of(CRUD.CREATE, CRUD.READ));

     private Container container;

     private int typeOrdinal;

     private Proxy proxy;

     private transient EntityManagerESImpl entityManager;

     private boolean managedMode;

     private CRUD lastOperation;

     private Long creationId;

     protected ProxyAspect() {
     }

     public ProxyAspect(int typeOrdinal, EntityManagerESImpl entityManager, boolean changeGenerationEnabled, CRUD obtainedBy) {
          this.typeOrdinal = typeOrdinal;
          this.entityManager = entityManager;
          this.managedMode = changeGenerationEnabled;
          lastOperation = obtainedBy;
          if (!INITIAL_CLIENT_OPS.contains(obtainedBy)) {
               throw new IllegalArgumentException("a managed instance cannot be obtained by " + obtainedBy);
          }
          if (lastOperation == CRUD.CREATE) {
               this.creationId = CREATION_ID_GENERATOR.getAndIncrement();
          }
     }

     /**
      *
      * @return a client-unique technical id defined for creations only
      */
     public Long getCreationId() {
          return creationId;
     }

     public boolean isCreatedOnClient() {
          return creationId != null;
     }

     public ManagedTypeESImpl getType() {
          return (ManagedTypeESImpl) AbstractMetamodelES.getInstance().getType(typeOrdinal);
     }

     public Proxy getProxy() {
          return proxy;
     }

     public <T> void setProxy(Proxy<T> proxy) {
          final SortedSet<PluralAttributeESImpl> pluralAttributes = proxy.__getProxyAspect().getType().getPluralAttributes();
          for (PluralAttributeES a : pluralAttributes) {

          }
     }

     public Container getContainer() {
          return container;
     }

     public void setContainer(Container container) {
          if (getType().getPersistenceType() != Type.PersistenceType.EMBEDDABLE) {
               throw new UnsupportedClassVersionError("not an embeddable");
          } else {
               if (this.container == null) {
                    this.container = container;
               } else {
                    throw new IllegalStateException();
               }
          }
     }

     public CRUD getLastOperation() {
          return lastOperation;
     }

     public void setLastOperation(CRUD lastOperation) {
          this.lastOperation = lastOperation;
     }

     /**
      * Gets the {@code transient} {@link EntityManagerESImpl} {@code this} is
      * managed by
      *
      * @return
      */
     public EntityManagerESImpl getEntityManager() {
          return entityManager;
     }

     public void setEntityManager(EntityManagerESImpl entityManager) {
          if (this.entityManager == null) {
               this.entityManager = entityManager;
          } else {
               throw new IllegalStateException("this' managed type instance is allready managed by " + this.entityManager);
          }
     }

     public boolean isManagedMode() {
          return entityManager != null && managedMode;
     }

     public void setManagedMode(boolean managedMode) {
          this.managedMode = managedMode;
     }

}
