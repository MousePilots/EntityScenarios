package org.mousepilots.es.model.impl;

/**
 * @author Roy Cleven
 * @version 1.0, 25-11-2015
 * @param <T> the type whose constructor needs to be invoked.
 */
public interface Constructor<T> {

    /**
     * Invoke the no-arg constructor.
     * @return a new instance of {@code T}
     */
    public T invoke();
}