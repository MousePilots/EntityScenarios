package org.mousepilots.es.core.model.impl;

import org.mousepilots.es.core.model.BindableES;

/**
 * This class implements the BindableES interface.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 * @param <T> The type that will be bound.
 */
public class BindableESImpl<T> implements BindableES<T> {

    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    /**
     * Create a new instance of this class.
     * @param bindableType the {@link BindableType} of this bindable.
     * @param bindableJavaType  the java type of this bindable.
     */
    public BindableESImpl(BindableType bindableType,
            Class<T> bindableJavaType) {
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