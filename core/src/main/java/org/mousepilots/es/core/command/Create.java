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
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <TD>
 */
public abstract class Create<T, TD extends ManagedTypeES<T>> extends AbstractCommand<T, TD> {

    protected Create() {
        super();
    }

    protected Create(TD typeDescriptor, EntityManagerImpl entityManager) {
        super(entityManager, typeDescriptor);
    }

    @Override
    public final CRUD getOperation() {
        return CRUD.CREATE;
    }
    
    protected Proxy<T> createProxy() {
        return getType().createProxy();
    }


    @Override
    protected void doExecuteOnClient(){
        final Proxy<T> proxy = createProxy();
        final ProxyAspect proxyAspect = proxy.__getProxyAspect();
        proxyAspect.setCreate(this);
        getClientEntityManager().manage(proxy);
        super.setProxy(proxy);
    }

    @Override
    protected void doUndo() {
        final Proxy<T> proxy = getProxy();
        super.setProxy(null);
        getClientEntityManager().unManage(proxy);
        final ProxyAspect proxyAspect = proxy.__getProxyAspect();
        proxyAspect.setCreate(null);
    }

    @Override
    protected void doRedo() {
        this.doExecuteOnClient();
    }

    @Override @GwtIncompatible
    public void executeOnServer(ServerContext serverContext) {
        setRealSubject(getType().createInstance());
    }

}
