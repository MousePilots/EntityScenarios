package org.mousepilots.es.model.impl;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.PluralAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C> implements PluralAttributeES<T, C, E> {

    private final CollectionType collectionType;
    private final Type<E> elementType;
    private final BindableType bindableType;
    private final Class<E> bindableJavaType;

    public PluralAttributeESImpl(CollectionType collectionType, Type<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, PersistentAttributeType persistentAttributeType, MemberES javaMember, int ordinal, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType, Class<C> javaType) {
        super(name, persistentAttributeType, javaMember, ordinal, readOnly, collection, association, declaringType, javaType);
        this.collectionType = collectionType;
        this.elementType = elementType;
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
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
        return bindableType;
    }

    @Override
    public Class<E> getBindableJavaType() {
        return bindableJavaType;
    }
}