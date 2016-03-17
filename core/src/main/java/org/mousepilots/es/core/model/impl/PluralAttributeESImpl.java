package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public abstract class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C> implements PluralAttributeES<T, C, E> {
    
    private final int elementTypeOrdinal;
    private final CollectionType collectionType;
    private final BindableType bindableType;
    private final Class<E> bindableJavaType;

    public PluralAttributeESImpl(
            String name, 
            int ordinal, 
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            int typeOrdinal, 
            PersistentAttributeType persistentAttributeType, 
            MemberES javaMember, 
            boolean readOnly, 
            AssociationES association, 
            ManagedTypeES<T> declaringType, 
            Constructor<HasValue> hasValueChangeConstructor, 
            CollectionType collectionType,
            int elementTypeOrdinal,
            BindableType bindableType,
            Class<E> bindableJavaType){
        super(name, ordinal, superOrdinal, subOrdinals, typeOrdinal, persistentAttributeType, javaMember, readOnly, association, declaringType, hasValueChangeConstructor);
        this.collectionType = collectionType;
        this.elementTypeOrdinal = elementTypeOrdinal;
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    @Override
    public CollectionType getCollectionType() {
        return collectionType;
    }

    @Override
    public TypeES<E> getElementType() {
        return AbstractMetamodelES.getInstance().getType(elementTypeOrdinal);
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
    public HasValue wrap(C values) {
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
                final Collection ids = IdentifiableTypeUtils.addIds(iet, (Collection) values, (Collection) createEmpty());
                //set the List<I> of ids
                retval.setValue(ids);
                return retval;
            }

            default:
                throw new IllegalStateException("bad persistence attribute type for " + this + ": " + persistentAttributeType);
        }
    }

     @Override
     public boolean isCollection() {
          return true;
     }
    
    @Override
    public String toString() {
        return StringUtils.createToString(getClass(), Arrays.asList(
                "name",     getName(),
                "ordinal",  getOrdinal(),
                "javaType", getJavaType().getName() + "<" + getElementType().getJavaType().getName() + ">"
        ));
    }
    
    
}
