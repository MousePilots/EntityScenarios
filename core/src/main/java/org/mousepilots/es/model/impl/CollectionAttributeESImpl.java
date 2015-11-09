package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Collection<E>, E> implements CollectionAttributeES<T, E> {

    public CollectionAttributeESImpl(Type<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, PersistentAttributeType persistentAttributeType, MemberES javaMember, int ordinal, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType, Class<Collection<E>> javaType) {
        super(CollectionType.COLLECTION, elementType, bindableType, bindableJavaType, name, persistentAttributeType, javaMember, ordinal, readOnly, collection, association, declaringType, javaType);
    }
}