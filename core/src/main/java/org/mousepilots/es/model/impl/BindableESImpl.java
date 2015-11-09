package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.BindableES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type that will be bound.
 */
public class BindableESImpl<T> implements BindableES<T> {

    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    public BindableESImpl(BindableType bindableType, Class<T> bindableJavaType) {
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    @Override
    public BindableType getBindableType() {
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableJavaType;
    }
}