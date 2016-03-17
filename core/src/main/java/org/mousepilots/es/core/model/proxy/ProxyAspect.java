/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import java.io.Serializable;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.Create;
import org.mousepilots.es.core.model.impl.container.Container;
import org.mousepilots.es.core.model.impl.container.EmbeddableContainer;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerESImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;

/**
 *
 * @author jgeenen
 */
public final class ProxyAspect<T> implements Serializable{

     private int typeOrdinal;
     
     private Container container;

     private transient EntityManagerESImpl entityManager;

     private boolean managedMode=false;

     private Create<T,? extends ManagedTypeES<T>> create;
     
     private Command<T,? extends ManagedTypeES<T>> delete;

     protected ProxyAspect(){}

     protected ProxyAspect(int typeOrdinal){
          this.typeOrdinal = typeOrdinal;
     }
     
     public int getTypeOrdinal() {
          return typeOrdinal;
     }

     public void setCreate(Create create) {
          if(this.create==null || create==null){
               this.create = create;
          } else {
               throw new IllegalStateException("create allready set");
          }
     }
     
     public <C extends Create<T,? extends ManagedTypeES<T>>> C getCreate() {
          return (C) create;
     }

     public void setDelete(Command<T,? extends ManagedTypeES<T>> delete) {
          if(this.delete==null || delete==null){
               this.delete = delete;
          } else {
               throw new IllegalStateException("delete allready set");
          }
     }

     public Command getDelete() {
          return delete;
     }
     
     

     public boolean isCreated() {
          return create!=null;
     }

     public ManagedTypeESImpl<T> getType() {
          return (ManagedTypeESImpl) AbstractMetamodelES.getInstance().getType(typeOrdinal);
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
