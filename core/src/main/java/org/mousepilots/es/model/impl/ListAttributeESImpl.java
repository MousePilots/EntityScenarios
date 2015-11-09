package org.mousepilots.es.model.impl;

import java.util.List;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E> extends PluralAttributeESImpl<T, List<E>, E> implements ListAttributeES<T, E> {

    public ListAttributeESImpl(Type<E> elementType, BindableType bindableType, Class<E> bindableJavaType, String name, PersistentAttributeType persistentAttributeType, MemberES javaMember, int ordinal, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType, Class<List<E>> javaType) {
        super(CollectionType.LIST, elementType, bindableType, bindableJavaType, name, persistentAttributeType, javaMember, ordinal, readOnly, collection, association, declaringType, javaType);
    }
}