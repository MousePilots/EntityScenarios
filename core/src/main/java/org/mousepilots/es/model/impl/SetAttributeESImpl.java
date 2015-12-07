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
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E>
        extends PluralAttributeESImpl<T, Set<E>, E, Set<E>> implements SetAttributeES<T, E> {

    @Override
    public Set<E> createEmpty() {
        return new HashSet<>();
    }

    /**
     * Create a new instance.
     * @param elementType the type of the elements for this set attribute.
     * @param bindableType the {@link BindableType} of this set attribute.
     * @param bindableJavaType the java type that is bound for this set attribute.
     * @param name the name of this set attribute.
     * @param ordinal the ordinal of this set attribute.
     * @param javaType the java type of this set attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this set attribute.
     * @param javaMember the java {@link Member} representing this set attribute.
     * @param readOnly whether or not this set attribute is read only.
     * @param association whether or not this set attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this set attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this attribute for a DTO.
     */
    public SetAttributeESImpl(TypeES<E> elementType, BindableType bindableType,
            Class<E> bindableJavaType, String name, int ordinal,
            Class<Set<E>> javaType, PersistentAttributeType persistentAttributeType,
            MemberES javaMember, boolean readOnly, boolean association,
            ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(CollectionType.SET, elementType, bindableType, bindableJavaType,
                name, ordinal, javaType, persistentAttributeType, javaMember,
                readOnly, association, declaringType, hasValueChangeConstructor,
                hasValueDtoConstructor);
    }

    @Override
    public boolean isCollection() {
        return true;
    }
}