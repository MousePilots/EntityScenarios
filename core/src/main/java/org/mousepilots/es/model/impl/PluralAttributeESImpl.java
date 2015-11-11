package org.mousepilots.es.model.impl;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.PluralAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C>
    implements PluralAttributeES<T, C, E> {

    private final CollectionType collectionType;
    private final Type<E> elementType;
    private final BindableParameters<E> bindableParameters;

    public PluralAttributeESImpl(CollectionType collectionType,
            Type<E> elementType, BindableParameters<E> bindableParameters,
            AttributeParameters<C> attributeParameters) {
        super(attributeParameters);
        this.collectionType = collectionType;
        this.elementType = elementType;
        this.bindableParameters = bindableParameters;
    }

    @Override
    public CollectionType getCollectionType() {
        return collectionType;
    }

    @Override
    public Type<E> getElementType() {
        return elementType;
    }

    @Override
    public BindableType getBindableType() {
        return bindableParameters.getBindableType();
    }

    @Override
    public Class<E> getBindableJavaType() {
        return bindableParameters.getBindableJavaType();
    }
}