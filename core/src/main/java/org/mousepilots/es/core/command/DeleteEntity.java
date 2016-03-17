/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.IdentifiableTypeCommand;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <ID>
 */
public final class DeleteEntity<T,ID> extends AbstractCommand<T, EntityTypeES<T>> implements IdentifiableTypeCommand<T, ID, EntityTypeES<T>>{

     private HasValue<ID> id;
     
     private transient Proxy<T> deleted;
     
     public DeleteEntity(EntityTypeES<T> type, Proxy<T> proxy) {
          super(null, type);
          this.id = WrapperUtils.getWrappedId(type, proxy.__subject());
          setProxy(proxy);
     }

     @Override
     public CRUD getOperation() {
          return CRUD.DELETE;
     }

     @Override
     public ID getId() {
          return id.getValue();
     }

     @Override
     protected void doExecuteOnClient(){
          deleted = getProxy();
          final ProxyAspect aspect = deleted.__getProxyAspect();
          aspect.setEntityManager(null);
          aspect.setDelete(this);
     }

     @Override
     protected void doUndo() {
          final ProxyAspect aspect = deleted.__getProxyAspect();
          aspect.setDelete(null);
          aspect.setEntityManager(getClientEntityManager());
          setProxy(deleted);
     }

     @Override
     protected void doRedo() {
          doExecuteOnClient();
     }

     @Override
     public void executeOnServer(ServerContext serverContext) {
          final EntityManager entityManager = serverContext.getEntityManager();
          final Class<T> javaType = getType().getJavaType();
          final ID id = getId();
          final T subject = entityManager.find(javaType, id);
          setRealSubject(subject);
          entityManager.remove(subject);
          serverContext.onExecuteOnServer(this);
     }

     @Override
     public <R, A> R accept(CommandVisitor<R, A> listener, A arg) {
          return listener.visit(this, arg);
     }     
     


     
}
