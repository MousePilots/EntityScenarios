package org.mousepilots.es.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.metamodel.SetAttribute;

/**
 * Instances of the type SetAttribute represent persistent
 * java.util.Set-valued attributes.
 *
 * @param <X> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 * @see PluralAttributeES
 * @see SetAttribute
 */
public interface SetAttributeES<X, E> extends PluralAttributeES<X, java.util.Set<E>, E>, SetAttribute<X, E>{

    @Override
    public default CollectionType getCollectionType() {
        return CollectionType.SET;
    }
    
    @Override
    public default <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public default Set<E> createEmpty() {
        return new HashSet<>();
    }
    
    

}