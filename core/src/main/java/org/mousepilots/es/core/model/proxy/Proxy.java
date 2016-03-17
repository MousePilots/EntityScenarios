/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import java.io.Serializable;

/**
 * Represents a Proxy for a remote managed instance. 
 * @author jgeenen
 * @param <T> the type of the proxied subject
 */
public interface Proxy<T> extends Serializable{
    
    /**
     * @return {@code this}' {@link ProxyAspect} which is never {@code null}
     */
    public ProxyAspect<T> __getProxyAspect();
    
    /**
     * @return a typecast of {@code this} to {@code T}
     */
    default T __subject(){
        return (T) this;
    }
    
}
