package org.mousepilots.es.core.model;

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
public interface PluralAttributeES<X, C, E> extends AttributeES<X, C>, PluralAttribute<X, C, E>, BindableES<E> {

    @Override
    TypeES<E> getElementType();
    
    /**
     * @return a new and empty {@link Collection} or {@link Map} suitable as a value for {@code this}
     */
    C createEmpty();
}