package org.mousepilots.es.model;

import javax.persistence.metamodel.ManagedType;

/**
 * Instances of the type {@link ManagedTypeES} represent entity, mapped
 * superclass, and embeddable types.
 * @param <T> The represented type.
 * @see TypeES
 * @see ManagedType
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface ManagedTypeES<T> extends TypeES<T>,ManagedType<T>{
    
}