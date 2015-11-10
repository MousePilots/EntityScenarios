package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The represented type that contains the attribute.
 * @param <TA> The type of the represented attribute.
 */
public class AttributeESImpl<T, TA> implements AttributeES<T, TA>{

    private final AttributeTypeParameters<TA> attributeTypeParameters;
    private final PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final boolean readOnly, collection, association;
    private final ManagedTypeES declaringType;
    private final Map<AssociationTypeES, AssociationES> associations
            = new EnumMap<>(AssociationTypeES.class);

    public AttributeESImpl(AttributeTypeParameters<TA> attributeTypeParameters,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES declaringType) {
        this.attributeTypeParameters = attributeTypeParameters;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.readOnly = readOnly;
        this.collection = collection;
        this.association = association;
        this.declaringType = declaringType;
    }
    
    @Override
    public boolean isReadOnly() {
        return this.readOnly;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return this.associations.containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return this.associations.get(type);
    }

    @Override
    public MemberES getJavaMember() {
        //Maybe not allowed to get the member from the constructor?
        return javaMember;
    }

    @Override
    public String getName() {
        return attributeTypeParameters.getName();
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public ManagedTypeES<T> getDeclaringType() {
        return declaringType;
    }

    @Override
    public Class<TA> getJavaType() {
        return attributeTypeParameters.getJavaType();
    }

    @Override
    public boolean isAssociation() {
        return association;
    }

    @Override
    public boolean isCollection() {
        return collection;
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    @Override
    public int getOrdinal() {
        return attributeTypeParameters.getOrdinal();
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
}