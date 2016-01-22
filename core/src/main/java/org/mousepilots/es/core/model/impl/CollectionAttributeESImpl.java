package org.mousepilots.es.core.model.impl;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.TypeES;

/**
 * This class implements the CollectionAttributeES interface.
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E>
        extends PluralAttributeESImpl<T, Collection<E>, E, Collection<E>>
        implements CollectionAttributeES<T, E> {

    @Override
    public Collection<E> createEmpty() {
        return new ArrayList<>();
    }

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this collection attribute.
     * @param bindableType the {@link BindableType} of this collection attribute.
     * @param bindableJavaType the java type that is bound for this collection attribute.
     * @param name the name of this collection attribute.
     * @param ordinal the ordinal of this collection attribute.
     * @param javaType the java type of this collection attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this collection attribute.
     * @param javaMember the java {@link Member} representing this collection attribute.
     * @param readOnly whether or not this collection attribute is read only.
     * @param association whether or not this collection attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this collection attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this attribute for a DTO.
     */
    public CollectionAttributeESImpl(TypeES<E> elementType,
            BindableType bindableType, Class<E> bindableJavaType,
            String name, int ordinal, Class<Collection<E>> javaType,
            PersistentAttributeType persistentAttributeType,
            MemberES javaMember, boolean readOnly, boolean association,
            ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(CollectionType.COLLECTION, elementType, bindableType,
                bindableJavaType, name, ordinal, javaType,
                persistentAttributeType, javaMember, readOnly, association,
                declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public <T> T accept(AttributeVisitor<T> visitor) {
        return visitor.visit(this);
    }    
}