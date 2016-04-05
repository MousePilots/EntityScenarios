package org.mousepilots.es.core.model;

import java.util.Collection;
import javax.persistence.metamodel.CollectionAttribute;

/**
 * Instances of the type {@link CollectionAttributeES} represent persistent
 * {@link Collection}-valued attributes.
 * @param <T> The type the represented {@link Collection} belongs to.
 * @param <E> The element type of the represented {@link Collection}.
 * @see PluralAttributeES
 * @author Roy Cleven
 * @version 1.0, 11-11-2015
 */
public interface CollectionAttributeES<T, E> extends PluralAttributeES<T, java.util.Collection<E>, E>, CollectionAttribute<T, E> {

    @Override
    public default CollectionType getCollectionType() {
        return CollectionType.COLLECTION;
    }

    @Override
    public default <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    
    
}