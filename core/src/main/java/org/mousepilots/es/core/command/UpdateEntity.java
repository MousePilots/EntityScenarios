/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.ref.IdentifiableReference;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <ID>
 * @param <V>
 * @param <A>
 * @param <AD>
 */
public final class UpdateEntity<E, ID, V, A, AD extends AttributeES<? super E, A>> extends Update<E, EntityTypeES<E>, A, AD,IdentifiableReference<E, ID, V>> implements IdentifiableTypeCommand<E, ID, EntityTypeES<E>>{

     private UpdateEntity(){}

     public UpdateEntity(Proxy<E> proxy, AD attribute,UpdateAttribute<E,A,AD,?> attributeUpdate){
          super(proxy, attribute,IdentifiableReference::new,attributeUpdate);
     }

     @Override
     public final ID getId() {
          if(super.isSubjectCreated()){
               return IdentifiableTypeUtils.getId(getType(), getCreateCommand().getRealSubject());
          } else {
               return getSerializableReference().getId();
          }
     }

     public V getVersion() {
          if(super.isSubjectCreated()){
               return IdentifiableTypeUtils.getVersion(getType(), getCreateCommand().getRealSubject());
          } else {
               return getSerializableReference().getVersion();
          }
     }

     @Override
     public <R, A> R accept(CommandVisitor<R, A> listener, A arg) {
          return listener.visit(this, arg);
     }
     
     
}
