package org.mousepilots.es.model.impl;

/**
 * @author Roy Cleven
 * @param <T>
 * @param <V>
 */
public interface Getter<T,V> {
    
    public V invoke(T object);
    
}