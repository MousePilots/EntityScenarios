package org.mousepilots.es.model.impl;

import java.util.Objects;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The represented type that contains the attribute.
 * @param <TA> The type of the represented attribute.
 */
public class AttributeESImpl<T, TA> implements AttributeES<T, TA>{

    private final AttributeParameters<TA> attributeParameters;

    public AttributeESImpl(AttributeParameters<TA> attributeParameters) {
        this.attributeParameters = attributeParameters;
        if (attributeParameters == null
                || attributeParameters.getAttributeTypeParameters() == null) {
            throw new IllegalArgumentException(
                "The attribute parameters or attribute type parameters cannot be null");
        }
    }

    @Override
    public boolean isReadOnly() {
        return attributeParameters.isReadOnly();
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return attributeParameters.getAssociations().containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return attributeParameters.getAssociations().get(type);
    }

    @Override
    public MemberES getJavaMember() {
        //Maybe not allowed to get the member from the constructor?
        return attributeParameters.getJavaMember();
    }

    @Override
    public String getName() {
        return attributeParameters.getAttributeTypeParameters().getName();
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return attributeParameters.getPersistentAttributeType();
    }

    @Override
    public ManagedTypeES<T> getDeclaringType() {
        return attributeParameters.getDeclaringType();
    }

    @Override
    public Class<TA> getJavaType() {
        return attributeParameters.getAttributeTypeParameters().getJavaType();
    }

    @Override
    public boolean isAssociation() {
        return attributeParameters.isAssociation();
    }

    @Override
    public boolean isCollection() {
        return attributeParameters.isCollection();
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    @Override
    public int getOrdinal() {
        return attributeParameters.getAttributeTypeParameters().getOrdinal();
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