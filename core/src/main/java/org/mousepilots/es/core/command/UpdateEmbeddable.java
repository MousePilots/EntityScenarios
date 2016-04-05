/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.ref.EmbeddableReference;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 * @param <AD>
 */
public final class UpdateEmbeddable<E,A,AD extends AttributeES<?super E,A>> extends Update<E,EmbeddableTypeES<E>,A,AD,EmbeddableReference<E>>{

     private UpdateEmbeddable(){ 
          super(); 
     }

     public UpdateEmbeddable(Proxy<E> proxy, AD attribute, UpdateAttribute<E,A,AD,?> attributeUpdate) {
          super(proxy, attribute,EmbeddableReference::new,attributeUpdate);
     }

     @Override @GwtIncompatible
     public E resolveSubject(ServerContext serverContext) {
          if(isSubjectCreated()){
               return getCreateCommand().getRealSubject();
          } else {
               return getReference().resolve(serverContext);
          }
     }

     @Override
     public <O, I> O accept(CommandVisitor<O, I> listener, I arg) {
          return listener.visit(this, arg);
     }
     
}
