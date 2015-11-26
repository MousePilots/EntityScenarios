package org.mousepilots.es.model;

import javax.persistence.metamodel.PluralAttribute;

/**
 * Instances of the type PluralAttribute represent
 * persistent collection-valued attributes.
 *
 * @param <X> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 * @see AttributeES
 * @see PluralAttribute
 */
public interface PluralAttributeES<X, C, E, CA> extends AttributeES<X, C,CA>, PluralAttribute<X, C, E>, BindableES<E> {

    @Override
    TypeES<E> getElementType();
}