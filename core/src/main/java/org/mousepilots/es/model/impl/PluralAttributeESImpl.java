package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.PluralAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C>
    implements PluralAttributeES<T, C, E> {

    private final CollectionType collectionType;
    private final TypeES<E> elementType;
    private final BindableType bindableType;
    private final Class<E> bindableJavaType;

    public PluralAttributeESImpl(CollectionType collectionType, TypeES<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, int ordinal, Class<C> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<T> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
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
    public TypeES<E> getElementType() {
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

    @Override
    public HasValue wrapForChange(C value) {
        switch(getElementType().getPersistenceType()){
            case MAPPED_SUPERCLASS:
            case ENTITY:
                //Make collection of IDs
                break;
            case BASIC:
            case EMBEDDABLE:
            default: {

            }

        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HasValue wrapForDTO(C value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}