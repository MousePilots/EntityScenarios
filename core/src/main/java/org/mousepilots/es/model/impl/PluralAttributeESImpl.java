package org.mousepilots.es.model.impl;

import java.util.Collection;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.PluralAttributeES;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.util.IdentifiableUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public abstract class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C>
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
    public HasValue wrapForChange(C values) {
          //default implementation for java.util.Collection subclasses
        // TODO: MapAttributeESImpl needs an override!
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
    public HasValue wrapForDTO(C value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}