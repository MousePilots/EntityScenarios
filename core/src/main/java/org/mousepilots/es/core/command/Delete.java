/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;

/**
 *
 * @author jgeenen
 * @param <T>
 * @param <TD>
 */
public abstract class Delete<T, TD extends ManagedTypeES<T>> extends AbstractCommand<T, TD> {

    protected Delete() {
        super();
    }

    private Delete(Proxy<T> proxy, ProxyAspect<T> proxyAspect) {
        super(proxyAspect.getEntityManager(), (TD) proxyAspect.getType());
        super.setProxy(proxy);
    }

    protected Delete(Proxy<T> proxy) {
        this(proxy, proxy.__getProxyAspect());
    }
    
     @Override
     public final CRUD getOperation() {
          return CRUD.DELETE;
     }    

    @Override
    protected void doExecuteOnClient() {
        final Proxy<T> proxy = getProxy();
        proxy.__getProxyAspect().setDelete(this);
        getClientEntityManager().unManage(proxy);
    }

    @Override
    protected void doRedo() {
        doExecuteOnClient();
    }

    @Override
    protected void doUndo() {
        final Proxy<T> proxy = getProxy();
        proxy.__getProxyAspect().setDelete(null);
        getClientEntityManager().manage(proxy);
    }
    
    
    
    
     
     
     
}
