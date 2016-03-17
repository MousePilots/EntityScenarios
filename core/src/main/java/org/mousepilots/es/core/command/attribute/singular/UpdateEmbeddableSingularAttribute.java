/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.singular;

import org.mousepilots.es.core.command.CreateEmbeddable;
import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 */
final class UpdateEmbeddableSingularAttribute<E,A> extends AbstractUpdateAttribute<E, A, SingularAttributeES<? super E, A>> {
     
     private transient Proxy<A> oldEmbeddableValue, newEmbeddableValue;
     private CreateEmbeddable<A> newEmbeddableValueCreate; 

     private UpdateEmbeddableSingularAttribute(){}

     UpdateEmbeddableSingularAttribute(Proxy<E> updated, SingularAttributeES<? super E,A> attribute, OwnedSetter<A> superSetter, A newValue) {
          super(superSetter);
          this.oldEmbeddableValue = (Proxy<A>) attribute.getJavaMember().get(updated.__subject());
          this.newEmbeddableValue = (Proxy<A>) newValue;
          final ProxyAspect<A> newEmbeddableValuePA = this.newEmbeddableValue.__getProxyAspect();
          if(newEmbeddableValuePA.isCreated()){
               this.newEmbeddableValueCreate = (CreateEmbeddable<A>) newEmbeddableValuePA.getCreate();
          } else {
               throw new UnsupportedOperationException("re-association of a pre-existing embeddable value to another parent is not supported");
          }
     }

     @Override
     public void executeOnClient(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          this.getSuperSetter().set(newEmbeddableValue.__subject());
     }

     @Override
     public void undo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          this.getSuperSetter().set(oldEmbeddableValue.__subject());
     }

     @Override
     public void redo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
          executeOnClient(update);
     }

     @Override
     public void executeOnServer(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update, ServerContext serverContext) {
          final E parent = update.getRealSubject();
          final A value = newEmbeddableValueCreate.getRealSubject();
          update.getAttribute().getJavaMember().set(parent, value);
     }
}
