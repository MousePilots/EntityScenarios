package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.IdentifiableUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 * @param <CA> The type that will be wrapped by the wrapper methods.
 */
public abstract class PluralAttributeESImpl<T, C, E, CA> extends AttributeESImpl<T, C, CA>
        implements PluralAttributeES<T, C, E, CA> {

    private final CollectionType collectionType;
    private final TypeES<E> elementType;
    private final BindableType bindableType;
    private final Class<E> bindableJavaType;

    /**
     * Create a new instance of this class.
     *
     * @param collectionType the type of collection this plural attribute
     * represents.
     * @param elementType the type of the elements for this plural attribute.
     * @param bindableType the {@link BindableType} of this plural attribute.
     * @param bindableJavaType the java type that is bound for this plural
     * attribute.
     * @param name the name of this plural attribute.
     * @param ordinal the ordinal of this plural attribute.
     * @param javaType the java type of this plural attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of
     * this plural attribute.
     * @param javaMember the java {@link Member} representing this plural
     * attribute.
     * @param readOnly whether or not this plural attribute is read only.
     * @param association whether or not this plural attribute is part of an
     * association.
     * @param declaringType the {@link ManagedTypeES} that declared this plural
     * attribute.
     * @param hasValueChangeConstructor the constructor that will be used when
     * wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when
     * wrapping this attribute for a DTO.
     */
    public PluralAttributeESImpl(CollectionType collectionType,
            TypeES<E> elementType, BindableType bindableType,
            Class<E> bindableJavaType, String name, int ordinal,
            Class<C> javaType, PersistentAttributeType persistentAttributeType,
            MemberES javaMember, boolean readOnly, boolean association,
            ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(name, ordinal, javaType, persistentAttributeType, javaMember,
                readOnly, association, declaringType, hasValueChangeConstructor,
                hasValueDtoConstructor);
        this.collectionType = collectionType;
        this.elementType = elementType;
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    /**
     * Create an empty instance of a collection that is represented by this
     * plural attribute.
     *
     * @return a new empty instance of a collection.
     */
    public abstract C createEmpty();

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
    public HasValue wrapForChange(CA values, DtoType dtoType) {

        dtoType.assertSupported();
        //default implementation for java.util.Collection subclasses
        final HasValue retval = getHasValueChangeConstructor().invoke();
        final PersistentAttributeType persistentAttributeType = getPersistentAttributeType();
        switch (persistentAttributeType) {
            //basic or element collection
            //if T is the elementType, then we must return a HasValue<C<T>>
            case BASIC:
            case ELEMENT_COLLECTION: {
                retval.setValue(values);
                return retval;
            }

            //*-to-many relation of identifiables
            // if I is the Id-type for this' elementType, then we must return
            // a HasValue<C<I>>
            case ONE_TO_MANY:
            case MANY_TO_MANY: {
                //get the identifiable element type
                final IdentifiableTypeESImpl<E> iet = (IdentifiableTypeESImpl) getElementType();
                //collect the Ids of the identifiables
                final Collection ids = IdentifiableUtils.addIds(iet, (Collection) values, (Collection) createEmpty());
                //set the List<I> of ids
                retval.setValue(ids);
                return retval;
            }

            default:
                throw new IllegalStateException("bad persistence attribute type for " + this + ": " + persistentAttributeType);
        }
    }

    @Override
    public HasValue wrapForDTO(C value, DtoType dtoType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return super.toString() + ", PluralAttribute collectionType: "
                + getCollectionType() + ", elementType: "
                + getElementType().getJavaClassName();
    }
}