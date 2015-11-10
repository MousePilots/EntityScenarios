package org.mousepilots.es.model.impl;

/**
 * This class takes the most common attribute parameters and bundles
 * them to save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 10-11-2015
 */
public class AttributeTypeParameters<T> {
    
    private final String name;
    private final int ordinal;
    private final Class<T> javaType;

    public AttributeTypeParameters(String name, int ordinal,
            Class<T> javaType) {
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
    }    

    public String getName() {
        return name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public Class<T> getJavaType() {
        return javaType;
    }    
}