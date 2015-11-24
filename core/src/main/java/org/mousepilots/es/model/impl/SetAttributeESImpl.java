package org.mousepilots.es.model.impl;

import java.util.HashSet;
import java.util.Set;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E>
        extends PluralAttributeESImpl<T, Set<E>, E> implements SetAttributeES<T, E> {

    @Override
    public Set<E> createEmpty() {
        return new HashSet<>();
    }

    public SetAttributeESImpl(CollectionType collectionType, TypeES<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, int ordinal, Class<Set<E>> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<T> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
    }
}
