/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import java.io.Serializable;

/**
 * A proxy 
 * @author jgeenen
 * @param <T> 
 */
public interface Proxy<T> extends Serializable{
    
    
    
    /**
     * @return {@code this}' {@link ProxyAspect} which is never {@code null}
     */
    public ProxyAspect __getProxyAspect();
    
    /**
     * @return {@code this} {@link Proxy} subject: the 
     */
    default T __subject(){
        return (T) this;
    }
    
}
