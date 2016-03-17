/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.EntityManagerESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <TD>
 */
public abstract class Create<T,TD extends ManagedTypeES<T>> extends AbstractCommand<T,TD>{

     protected Create(){
          super();
     }

     protected Create(TD typeDescriptor,EntityManagerESImpl entityManager) {
          super(entityManager, typeDescriptor);
     }

     
     @Override
     public final CRUD getOperation() {
          return CRUD.CREATE;
     }

     @Override
     protected void doExecuteOnClient() {
          final Proxy<T> proxy = getType().createProxy();
          final ProxyAspect proxyAspect = proxy.__getProxyAspect();
          proxyAspect.setEntityManager(getClientEntityManager());
          proxyAspect.setCreate(this);
          proxyAspect.setManagedMode(true);
          super.setProxy(proxy);
     }

     @Override
     protected void doUndo() {
          final Proxy<T> proxy = getProxy();
          final ProxyAspect proxyAspect = proxy.__getProxyAspect();
          proxyAspect.setManagedMode(false);
          proxyAspect.setEntityManager(null);
          proxyAspect.setCreate(null);
          super.setProxy(null);
     }

     @Override
     protected void doRedo() {
          this.doExecuteOnClient();
     }
     
     @Override
     public void executeOnServer(ServerContext serverContext){
          setRealSubject(getType().createInstance());
     }
     
     
     
     
     
     
     
}
