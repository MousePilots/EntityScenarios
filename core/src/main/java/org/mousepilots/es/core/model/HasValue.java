package org.mousepilots.es.core.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Limits the GWT serializer generator for {@link AttributeES} attribute values {@code this}' concrete subclasses.
 *
 * @param <T> the wrapped value's type
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 */
public interface HasValue<T> extends Serializable {

    /**
    * @return the value wrapped by {@code this}
    */
    T getValue();

    /**
     * Set the value wrapped by {@code this}
     * @param value the new value.
     */
    void setValue(T value);

    
    

}