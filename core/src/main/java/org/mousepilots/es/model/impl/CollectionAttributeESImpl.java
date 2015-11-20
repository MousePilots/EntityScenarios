package org.mousepilots.es.model.impl;

import java.util.Collection;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, Collection<E>, E>
    implements CollectionAttributeES<T, E> {

    public CollectionAttributeESImpl(CollectionType collectionType, TypeES<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, int ordinal, Class<Collection<E>> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<T> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
    }
}