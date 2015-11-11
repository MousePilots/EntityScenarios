package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.BasicTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type for this BasicType.
 */
public class BasicTypeESImpl<T> extends TypeESImpl<T>
    implements BasicTypeES<T> {

    public BasicTypeESImpl(TypeParameters<T> typeParameters) {
        super(typeParameters);
    }
}