package org.mousepilots.es.model;

import javax.persistence.metamodel.IdentifiableType;

/**
 * Instances of the type {@link IdentifiableTypeES} represent entity or
 * mapped superclass types.
 * @param <T> The represented entity or mapped superclass type.
 * @see ManagedTypeES
 * @see IdentifiableType
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface IdentifiableTypeES<T>  extends ManagedTypeES<T>,IdentifiableType<T>{
    
}