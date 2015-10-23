/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.io.Serializable;

/**
 * 
 * Limits the GWT serializer generator for {@link AttributeES} attribute values {@code this}' concrete subclasses.
 * 
 * @param <T> the wrapped value's type
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 */
public interface Wrapper<T> extends Serializable {
    /**
    * @return the value wrapped by {@code this}
    */
    T unwrap();    
}
