package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.proxy.collection.ObservableList;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E> extends PluralAttributeESImpl<T, List<E>, E> implements ListAttributeES<T, E> {

    public ListAttributeESImpl(
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
        super(name, ordinal, superOrdinal, subOrdinals,typeOrdinal, persistentAttributeType, javaMember, readOnly, association, declaringType, hasValueChangeConstructor, collectionType, elementTypeOrdinal, bindableType, bindableJavaType);
    }

    @Override
    public List<E> createEmpty() {
        return new ArrayList<>();
    }

}