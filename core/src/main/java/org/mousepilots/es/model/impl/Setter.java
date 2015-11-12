package org.mousepilots.es.model.impl;

/**
 * @author Roy Cleven
 * @param <T>
 * @param <V>
 */
public interface Setter<T,V> {
    
    void invoke(T object, V value);
    
}