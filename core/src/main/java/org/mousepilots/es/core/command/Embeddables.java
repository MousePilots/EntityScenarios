/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.util.Collection;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;

/**
 *
 * @author geenenju
 */
public class Embeddables {

    private static final String MOVED_EMBEDDABLE_EXCEPTION_MESSAGE = " is no newly created embeddable";

    private static void doThrowNotNew(Proxy proxy) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(proxy + MOVED_EMBEDDABLE_EXCEPTION_MESSAGE);
    }

    public static void assertIfEmbeddableThenCreated(Proxy proxy) throws UnsupportedOperationException {
        final ProxyAspect proxyAspect = proxy.__getProxyAspect();
        final ManagedTypeESImpl type = proxyAspect.getType();
        if (type instanceof EmbeddableTypeES && !proxyAspect.isCreated()) {
            doThrowNotNew(proxy);
        }
    }

    public static <T> void assertIfEmbeddableThenCreated(TypeES<T> type, Collection<Proxy<T>> values) throws UnsupportedOperationException {
        if (type instanceof EmbeddableTypeES) {
            for(Proxy<T> value : values){
                if(!value.__getProxyAspect().isCreated()){
                    doThrowNotNew(value);
                }
            }
        }
    }

}
