/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collection;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author jgeenen
 */
public final class CollectionObserverImpl<E,EL> extends AbstractCollectionObserver<E,EL,Collection<EL>,CollectionAttributeES<? super E,EL>>{

    private CollectionObserverImpl(){}

    public CollectionObserverImpl(Proxy<E> proxy, CollectionAttributeES<? super E, EL> attributeDescriptor) {
        super(proxy, attributeDescriptor);
    }
    
}
