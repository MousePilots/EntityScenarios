package org.mousepilots.es.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E>
        extends PluralAttributeESImpl<T, List<E>, E>
        implements ListAttributeES<T, E> {

    public ListAttributeESImpl(CollectionType collectionType, TypeES<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, int ordinal, Class<List<E>> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<T> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
    }

    @Override
    public List<E> createEmpty() {
        return new ArrayList<>();
    }
}