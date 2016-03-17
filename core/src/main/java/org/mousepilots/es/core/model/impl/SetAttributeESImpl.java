package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.proxy.collection.Observable;
import org.mousepilots.es.core.model.proxy.collection.ObservableSet;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Set<E>, E> implements SetAttributeES<T, E> {

    @Override
    public Set<E> createEmpty() {
        return new HashSet<>();
    }

    public SetAttributeESImpl(
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
            Constructor<HasValue> hasValueDtoConstructor,
            CollectionType collectionType,
            int elementTypeOrdinal,
            BindableType bindableType,
            Class<E> bindableJavaType){
        super(name, ordinal, superOrdinal, subOrdinals, typeOrdinal, persistentAttributeType, javaMember, readOnly, association, declaringType, hasValueChangeConstructor, collectionType, elementTypeOrdinal, bindableType, bindableJavaType);
    }

}