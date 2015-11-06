package org.mousepilots.es.model.impl;

/**
 * @author Roy Cleven
 */
public interface Getter<T,V> {
    
    public V invoke(T object);
    
}