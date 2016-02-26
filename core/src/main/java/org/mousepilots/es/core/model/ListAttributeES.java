package org.mousepilots.es.core.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.ListAttribute;

/**
 * Instances of the type {@link ListAttributeES} represent persistent
 * {@link List}-valued attributes.
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 * @see PluralAttributeES
 * @see ListAttribute
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface ListAttributeES<T, E> extends PluralAttributeES<T, java.util.List<E>,E>, ListAttribute<T, E> {
    @Override
    public default <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public default List<E> createEmpty() {
        return new ArrayList<>();
    }
    
    

}