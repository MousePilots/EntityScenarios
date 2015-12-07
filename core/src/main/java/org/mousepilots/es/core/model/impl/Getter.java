package org.mousepilots.es.core.model.impl;

/**
 * @author Roy Cleven
 * @version 1.0, 25-11-2015
 * @param <T> the type to use the getter for.
 * @param <V> the type of value the getter returns.
 */
public interface Getter<T,V> {

    /**
     * Invoke the getter on specified {@code object}.
     * @param object the object to invoke the getter on.
     * @return the value the getter returns.
     */
    public V invoke(T object);

}