package org.mousepilots.es.model.impl;

/**
 * @author Roy Cleven
 */
public interface Setter<T,V> {
    
    void invoke(T object, V value);
    
}