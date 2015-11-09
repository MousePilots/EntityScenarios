package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.BindableES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public class BindableESImpl<T> implements BindableES<T> {

    @Override
    public Class<T> getBindableJavaType() {
        switch (getBindableType()){
            case PLURAL_ATTRIBUTE:
                //Return element type
                break;
            default:
                //Return entity type.
                break;
        }
        return null;
    }

    @Override
    public BindableType getBindableType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
