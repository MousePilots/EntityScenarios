package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.HasValue;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 */
public abstract class AbstractHasValueImpl<T> implements HasValue<T>{

    private T value;

    @Override
    public final T getValue() {
        return value;
    }

    @Override
    public final void setValue(T value) {
        this.value = value;
    }
}