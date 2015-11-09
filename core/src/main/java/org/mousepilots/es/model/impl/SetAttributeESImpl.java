package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SetAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Set<E>, E>implements SetAttributeES<T, E> {

    public SetAttributeESImpl(CollectionType collectionType, Type<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, PersistentAttributeType persistentAttributeType, MemberES javaMember, int ordinal, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType, Class<Set<E>> javaType) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, persistentAttributeType, javaMember, ordinal, readOnly, collection, association, declaringType, javaType);
    }
}