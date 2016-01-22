package org.mousepilots.es.core.model.impl;

import java.lang.reflect.Member;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.HasValue;

/**
 * This class is and abstract implementation of the AttributeES interface.
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> the represented type that contains the attribute.
 * @param <TA> the type of the represented attribute.
 * @param <TC> the type to wrap for a change.
 */
public abstract class AttributeESImpl<T, TA,TC> implements AttributeES<T, TA,TC> {

    private final String name;
    private final int ordinal;
    private final Class<TA> javaType;
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final boolean readOnly, association;
    private final ManagedTypeES<T> declaringType;
    private final Constructor<HasValue> hasValueChangeConstructor;
    private final Constructor<HasValue> hasValueDtoConstructor;
    private final Map<AssociationTypeES, AssociationES> associations
            = new EnumMap<>(AssociationTypeES.class);

    /**
     * Create a new instance of this class.
     * @param name the name of this attribute.
     * @param ordinal the ordinal of this attribute.
     * @param javaType the java type of this attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this attribute.
     * @param javaMember the java {@link Member} representing this attribute.
     * @param readOnly whether or not this attribute is read only.
     * @param association whether or not this attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this attribute for a DTO.
     */
    public AttributeESImpl(String name, int ordinal, Class<TA> javaType,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean association,
            ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.readOnly = readOnly;
        this.association = association;
        this.declaringType = declaringType;
        this.hasValueChangeConstructor = hasValueChangeConstructor;
        this.hasValueDtoConstructor = hasValueDtoConstructor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public Class<TA> getJavaType() {
        return javaType;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public MemberES getJavaMember() {
        return javaMember;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public boolean isAssociation() {
        return association;
    }

    @Override
    public ManagedTypeES<T> getDeclaringType() {
        return declaringType;
    }

    /**
     * Get the associations for this attribute.
     * @return The map of associations linked to this attribute.
     */
    public Map<AssociationTypeES, AssociationES> getAssociations() {
        return associations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(getName());
        hash = 83 * hash + Objects.hashCode(getJavaType());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return associations.containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return associations.get(type);
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    /**
     * Get the constructor that is used when wrapping the attribute for a change.
     * @return The constructor for wrapping a change.
     */
    public Constructor<HasValue> getHasValueChangeConstructor() {
        return hasValueChangeConstructor;
    }

    /**
     * Get the constructor that is used when wrapping the attribute for a DTO.
     * @return The constructor for wrapping a DTO.
     */
    public Constructor<HasValue> getHasValueDtoConstructor() {
        return hasValueDtoConstructor;
    }

    @Override
    public String toString() {
        return "Attribute name: " + getName() + ", ordinal: " + getOrdinal()
                + ", persistentAttributeType: " + getPersistentAttributeType()
                + ", javaClass: " + getJavaType().getName();
    }
}