package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.HasValue;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The represented type that contains the attribute.
 * @param <TA> The type of the represented attribute.
 */
public abstract class AttributeESImpl<T, TA> implements AttributeES<T, TA> {

    private final String name;
    private final int ordinal;
    private final Class<TA> javaType;
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final boolean readOnly, collection, association;
    private final ManagedTypeES<T> declaringType;
    private final Constructor<HasValue> hasValueChangeConstructor;
    private final Constructor<HasValue> hasValueDtoConstructor;
    private final Map<AssociationTypeES, AssociationES> associations
            = new EnumMap<>(AssociationTypeES.class);

    public AttributeESImpl(String name, int ordinal, Class<TA> javaType,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.readOnly = readOnly;
        this.collection = collection;
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
    public boolean isCollection() {
        return collection;
    }

    @Override
    public boolean isAssociation() {
        return association;
    }

    @Override
    public ManagedTypeES<T> getDeclaringType() {
        return declaringType;
    }

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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttributeESImpl<?, ?> other = (AttributeESImpl<?, ?>) obj;
        if (!Objects.equals(getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(getJavaType(), other.getJavaType())) {
            return false;
        }
        return true;
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

    public Constructor<HasValue> getHasValueChangeConstructor() {
        return hasValueChangeConstructor;
    }

    public Constructor<HasValue> getHasValueDtoConstructor() {
        return hasValueDtoConstructor;
    }
}