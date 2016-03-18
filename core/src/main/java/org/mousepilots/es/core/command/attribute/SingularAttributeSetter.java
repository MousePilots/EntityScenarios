/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateEmbeddable;
import org.mousepilots.es.core.command.UpdateEntity;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * Helper class which sole public method must be used in the setters of singular attributes of {@link Proxy}
 * @author geenenju
 */
public final class SingularAttributeSetter {

     private SingularAttributeSetter(){}

     private static <E, A> Update<E, ?, A, SingularAttributeES<? super E, A>, ?> createUpdate(
          Proxy<E> updated,
          SingularAttributeES<? super E, A> attribute,
          UpdateAttribute<E, A, SingularAttributeES<? super E, A>, A> attributeUpdate
     ){
          final ManagedTypeESImpl<E> updatedType = updated.__getProxyAspect().getType();
          switch (updatedType.getPersistenceType()) {
               case EMBEDDABLE:
                    return new UpdateEmbeddable<>(updated, attribute, attributeUpdate);
               case ENTITY:
                    return new UpdateEntity<>(updated, attribute, attributeUpdate);
               default:
                    throw new UnsupportedOperationException("unknown persistenceType for type " + updatedType);
          }
     }

     /**
      * Sets {@code newValue} on {@code proxy}'s {@link SingularAttributeES}, the {@code ordinal} of which is {@code singularAttributeOrdinal}
      * @param <E>
      * @param <A>
      * @param proxy
      * @param singularAttributeOrdinal
      * @param superSetter
      * @param newValue 
      */
     public static <E, A> void set(Proxy<E> proxy, int singularAttributeOrdinal, OwnedSetter<A> superSetter, A newValue) {
          final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
          final SingularAttributeES<? super E, A> attribute = (SingularAttributeES<? super E, A>) metamodel.getAttribute(singularAttributeOrdinal);
          if(proxy.__getProxyAspect().isManagedMode()) {
               final UpdateAttribute<E, A, SingularAttributeES<? super E, A>,A> attributeUpdate = new UpdateSingularAttribute<>(attribute,proxy,newValue);
               final Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update = createUpdate(proxy, attribute, attributeUpdate);
               update.executeOnClient();
          } else {
               superSetter.set(newValue);
          }
     }
}
