/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.singular;

import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <ID>
 * @param <A>
 */
final class UpdateIdentifiableSingularAttribute<E,ID, A> extends AbstractUpdateAttribute<E, A, SingularAttributeES<? super E, A>> {

     private HasValue<ID> newAttributeValueId;
     private transient A oldIdentifiable, newIdentifiable;
     
     private UpdateIdentifiableSingularAttribute(){}

     UpdateIdentifiableSingularAttribute(Proxy<E> owner, SingularAttributeES<? super E,A> attribute, OwnedSetter<A> superSetter, A newValue) {
          super(superSetter);
          this.oldIdentifiable = attribute.getJavaMember().get(owner.__subject());
          this.newIdentifiable = newValue;
          final IdentifiableTypeESImpl<A> attributeType = (IdentifiableTypeESImpl<A>) attribute.getType();
          final SingularAttributeES<? super A, ID> id = (SingularAttributeES) attributeType.getId();
          this.newAttributeValueId = HasValue.wrapAttribute(id, newIdentifiable);
     }

     
     
     @Override
     public void executeOnClient(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          getSuperSetter().set(newIdentifiable);
     }

     @Override
     public void undo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          getSuperSetter().set(oldIdentifiable);
     }

     @Override
     public void redo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          executeOnClient(update);
     }

     @Override
     public void executeOnServer(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update, ServerContext serverContext){
          final ID clientAttributeValueId = HasValue.getValueNullSafe(this.newAttributeValueId);
          final SingularAttributeES<? super E, A> attribute = update.getAttribute();
          final A valueToSet;
          if(clientAttributeValueId==null){
               valueToSet=null;
          } else {
               final IdentifiableTypeES<A> attributeType = (IdentifiableTypeES<A>) attribute.getType();
               final Class<A> javaType = attributeType.getJavaType();
               final ID serverId = serverContext.getServerId(attributeType, clientAttributeValueId);
               valueToSet = serverContext.getEntityManager().find(javaType, serverId);
          }
          attribute.getJavaMember().set(update.getRealSubject(),valueToSet);
     }
     
}
