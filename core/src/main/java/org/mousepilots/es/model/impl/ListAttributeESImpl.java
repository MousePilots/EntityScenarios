package org.mousepilots.es.model.impl;

import java.util.List;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ListAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, List<E>, E>
    implements ListAttributeES<T, E> {

    public ListAttributeESImpl(Type<E> elementType,
            BindableParameters<E> bindableParameters,
            AttributeParameters<List<E>> attributeParameters) {
        super(CollectionType.LIST, elementType, bindableParameters,
                attributeParameters);
    }
}