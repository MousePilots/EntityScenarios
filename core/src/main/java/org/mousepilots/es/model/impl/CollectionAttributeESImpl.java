package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, Collection<E>, E>
    implements CollectionAttributeES<T, E> {

    public CollectionAttributeESImpl(Type<E> elementType,
            BindableParameters<E> bindableParameters,
            AttributeParameters<Collection<E>> attributeParameters) {
        super(CollectionType.COLLECTION, elementType, bindableParameters,
                attributeParameters);
    }
}