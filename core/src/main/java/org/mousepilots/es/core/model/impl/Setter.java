package org.mousepilots.es.core.model.impl;

/**
 * @author Roy Cleven
 * @version 1.0, 25-11-2015
 * @param <T> the type to use the setter for.
 * @param <V> the type of value the setter takes as an argument.
 */
public interface Setter<T,V> {

    /**
     * Invoke the setter on specified {@code object}.
     * @param object the object to invoke the setter on.
     * @param value the new value to set using the setter.
     */
    void invoke(T object, V value);

}