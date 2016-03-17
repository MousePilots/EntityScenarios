/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.singular;

import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.attribute.singular.AbstractUpdateAttribute;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 */
final class UpdateBasicSingularAttribute<E, A> extends AbstractUpdateAttribute<E,A, SingularAttributeES<? super E, A>>{

     private HasValue<A> oldValue, newValue;

     private UpdateBasicSingularAttribute(){}

     UpdateBasicSingularAttribute(Proxy<E> updated, SingularAttributeES<? super E,A> attribute, OwnedSetter<A> superSetter, A newValue){
          super(superSetter);
          this.oldValue = HasValue.wrapAttribute(attribute, updated.__subject());
          this.newValue = HasValue.wrapAttributeValue(attribute, newValue);
     }
     
     private A getOldValue() {
          return HasValue.getValueNullSafe(oldValue);
     }

     private A getNewValue() {
          return HasValue.getValueNullSafe(newValue);
     }

     @Override
     public void executeOnClient(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          this.getSuperSetter().set(getNewValue());
     }


     @Override
     public void undo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          this.getSuperSetter().set(getOldValue());
     }

     @Override
     public void redo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          executeOnClient(update);
     }

     @Override
     public void executeOnServer(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update, ServerContext serverContext) {
          final E subject = update.getRealSubject();
          final MemberES<? super E, A> javaMember = update.getAttribute().getJavaMember();
          javaMember.set(subject, getNewValue());
     }
}
