package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * This class takes the most common attribute parameters and bundles
 * them to save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 */
public class AttributeParameters<TA> {

    private final AttributeTypeParameters<TA> attributeTypeParameters;
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final boolean readOnly, collection, association;
    private final ManagedTypeES declaringType;
    private final Map<AssociationTypeES, AssociationES> associations
            = new EnumMap<>(AssociationTypeES.class);

    public AttributeParameters(
            AttributeTypeParameters<TA> attributeTypeParameters,
            Attribute.PersistentAttributeType persistentAttributeType,
            MemberES javaMember, boolean readOnly, boolean collection,
            boolean association, ManagedTypeES declaringType) {
        this.attributeTypeParameters = attributeTypeParameters;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.readOnly = readOnly;
        this.collection = collection;
        this.association = association;
        this.declaringType = declaringType;
    }

    public AttributeTypeParameters<TA> getAttributeTypeParameters() {
        return attributeTypeParameters;
    }

    public Attribute.PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    public MemberES getJavaMember() {
        return javaMember;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isCollection() {
        return collection;
    }

    public boolean isAssociation() {
        return association;
    }

    public ManagedTypeES getDeclaringType() {
        return declaringType;
    }

    public Map<AssociationTypeES, AssociationES> getAssociations() {
        return associations;
    }
}