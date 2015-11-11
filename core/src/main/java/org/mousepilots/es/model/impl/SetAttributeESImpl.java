package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.SetAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, Set<E>, E>implements SetAttributeES<T, E> {

    public SetAttributeESImpl(Type<E> elementType,
            BindableParameters<E> bindableParameters,
            AttributeParameters<Set<E>> attributeParameters) {
        super(CollectionType.SET, elementType, bindableParameters,
                attributeParameters);
    }
}