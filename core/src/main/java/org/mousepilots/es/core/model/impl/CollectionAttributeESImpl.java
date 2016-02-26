package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.collections.ObservableCollection;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Collection<E>, E> implements CollectionAttributeES<T, E> {

    @Override
    public Collection<E> createEmpty() {
        return new ArrayList<>();
    }

    public CollectionAttributeESImpl(
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
        super(name, ordinal, superOrdinal, subOrdinals, typeOrdinal, persistentAttributeType, javaMember, readOnly, association, declaringType, hasValueChangeConstructor, collectionType, elementTypeOrdinal, bindableType, bindableJavaType);
    }
}