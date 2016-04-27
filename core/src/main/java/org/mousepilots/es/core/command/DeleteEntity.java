/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <ID>
 */
public final class DeleteEntity<E,ID> extends Delete<E, EntityTypeES<E>> implements IdentifiableTypeCommand<E, ID, EntityTypeES<E>>{

     private HasValue<ID> id;
     
     private DeleteEntity(){
         super();
     }
     
     public DeleteEntity(Proxy<E> proxy) {
          super(proxy);
          this.id = WrapperUtils.getWrappedId(getType(), proxy.__subject());
     }

     @Override
     public ID getId() {
          return id.getValue();
     }

     @Override @GwtIncompatible
     public void executeOnServer(ServerContext serverContext) {
          final EntityManager entityManager = serverContext.getEntityManager();
          final Class<E> javaType = getType().getJavaType();
          final E subject = entityManager.find(javaType, getId());
          setRealSubject(subject);
          entityManager.remove(subject);
          serverContext.onExecuteOnServer(this);
     }

     @Override
     public <R, A> R accept(CommandVisitor<R, A> listener, A arg) {
          return listener.visit(this, arg);
     }     
     


     
}
