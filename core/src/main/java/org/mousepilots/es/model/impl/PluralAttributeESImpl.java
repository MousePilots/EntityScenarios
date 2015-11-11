package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.AttributeParameters;
import org.mousepilots.es.model.PluralAttributeES;
import org.mousepilots.es.model.TypeES;

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
    private final TypeES<E> elementType;
    private final BindableParameters<E> bindableParameters;

    public PluralAttributeESImpl(CollectionType collectionType,
            TypeES<E> elementType, BindableParameters<E> bindableParameters,
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
    public TypeES<E> getElementType() {
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