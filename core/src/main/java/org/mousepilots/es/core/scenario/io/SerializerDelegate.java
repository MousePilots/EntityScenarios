/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.io;

/**
 *
 * @author jgeenen
 * @param <S>
 */
public abstract class SerializerDelegate<S extends Serializer> {

    private final S parent;
    private Object valueToSerialize;

    protected SerializerDelegate(S parent) {
        this.parent = parent;
    }
    
    /**
     * @return 
     */
    protected Object getValueToSerialize() {
        return valueToSerialize;
    }

    /**
     * @return the parent for which {@code this} is a delegate
     */
    protected S getParent() {
        return parent;
    }

    /**
     * Sets the value to serialize. Note that the value set is present only until the next call to {@link #setValueToSerialize(java.lang.Object)}!
     * @param valueToSerialize 
     */
    public void setValueToSerialize(Object valueToSerialize) {
        this.valueToSerialize = valueToSerialize;
    }
    
}
