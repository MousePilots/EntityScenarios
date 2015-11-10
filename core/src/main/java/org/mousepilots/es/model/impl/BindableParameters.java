package org.mousepilots.es.model.impl;

import javax.persistence.metamodel.Bindable.BindableType;

/**
 * This class takes the common bindable parameters and bundles them to
 * save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 10-11-2015
 */
public class BindableParameters<T> {
   
    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    public BindableParameters(BindableType bindableType,
            Class<T> bindableJavaType) {
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    public BindableType getBindableType() {
        return bindableType;
    }

    public Class<T> getBindableJavaType() {
        return bindableJavaType;
    }
}