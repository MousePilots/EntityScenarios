package org.mousepilots.es.model;

import java.util.Collection;

/**
 * Instances of the type {@link CollectionAttributeES} represent persistent
 * {@link Collection}-valued attributes.
 * @param <T> The type the represented {@link Collection} belongs to.
 * @param <E> The element type of the represented {@link Collection}.
 * @see PluralAttributeES
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface CollectionAttributeES<T, E> extends PluralAttributeES<T, java.util.Collection<E>, E> {

}