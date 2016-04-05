/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Set;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.SetObserver;

/**
 *
 * @author jgeenen
 * @param <E>
 * @param <EL>
 */
public final class SetObserverImpl<E,EL> extends AbstractCollectionObserver<E, EL, Set<EL>, SetAttributeES<? super E, EL>> implements SetObserver<EL>{

    private SetObserverImpl(){}

    public SetObserverImpl(Proxy<E> proxy, SetAttributeES<? super E, EL> attributeDescriptor) {
        super(proxy, attributeDescriptor);
    }
    
}
