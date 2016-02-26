package org.mousepilots.es.core.model;

import javax.persistence.metamodel.Bindable;

/**
 * Instances of the type {@link BindableES} represent object or attribute types that can be bound into a {@link Path}.
 * @param <T> The type that will be bound.
 * @see Bindable
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface BindableES<T> extends Bindable<T>{

}