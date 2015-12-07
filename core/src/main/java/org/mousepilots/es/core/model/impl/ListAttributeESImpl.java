package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E>
        extends PluralAttributeESImpl<T, List<E>, E, List<E>>
        implements ListAttributeES<T, E> {

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this list attribute.
     * @param bindableType the {@link BindableType} of this list attribute.
     * @param bindableJavaType the java type that is bound for this list attribute.
     * @param name the name of this list attribute.
     * @param ordinal the ordinal of this list attribute.
     * @param javaType the java type of this list attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this list attribute.
     * @param javaMember the java {@link Member} representing this list attribute.
     * @param readOnly whether or not this list attribute is read only.
     * @param association whether or not this list attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this list attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this attribute for a DTO.
     */
    public ListAttributeESImpl(TypeES<E> elementType, BindableType bindableType,
            Class<E> bindableJavaType, String name, int ordinal,
            Class<List<E>> javaType,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean association, ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(CollectionType.LIST, elementType, bindableType, bindableJavaType,
                name, ordinal, javaType, persistentAttributeType, javaMember,
                readOnly, association, declaringType, hasValueChangeConstructor,
                hasValueDtoConstructor);
    }

    @Override
    public List<E> createEmpty() {
        return new ArrayList<>();
    }

    @Override
    public boolean isCollection() {
        return true;
    }
}