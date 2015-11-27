package org.mousepilots.es.maven.model.generator.model;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.ManagedTypeDescriptor;
import org.mousepilots.es.model.AssociationTypeES;

/**
 * Descriptor that describes an association between two attributes.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class AssociationDescriptor {

    private final AttributeDescriptor sourceAttribute;
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final boolean owner;
    private final ManagedTypeDescriptor targetTypeDescriptor;
    private final AttributeDescriptor inverseTargetAttributeDescriptor;
    private final AssociationTypeES associationInverseTargetAttributeType;

    /**
     * Create a new instance of this class.
     * @param sourceAttribute the source attribute of this association.
     * @param persistentAttributeType the {@link PersistentAttributeType} for this association.
     * @param owner whether or not the {@code sourceAttribute} is the owner of this association.
     * @param targetTypeDescriptor the {@link ManagedTypeDescriptor} that represents the target of this association.
     * @param inverseTargetAttributeDescriptor the {@link AttributeDescriptor} that is the inverse of this association if it is bidirectional.
     * @param associationInverseTargetAttributeType the {@link AssociationTypeES} for the inverse association.
     */
    public AssociationDescriptor(AttributeDescriptor sourceAttribute,
            Attribute.PersistentAttributeType persistentAttributeType,
            boolean owner, ManagedTypeDescriptor targetTypeDescriptor,
            AttributeDescriptor inverseTargetAttributeDescriptor,
            AssociationTypeES associationInverseTargetAttributeType) {
        this.sourceAttribute = sourceAttribute;
        this.persistentAttributeType = persistentAttributeType;
        this.owner = owner;
        this.targetTypeDescriptor = targetTypeDescriptor;
        this.inverseTargetAttributeDescriptor = inverseTargetAttributeDescriptor;
        this.associationInverseTargetAttributeType = associationInverseTargetAttributeType;
    }

    /**
     * Get the {@code sourceAttribute} of this association.
     * @return the source attribute.
     */
    public AttributeDescriptor getSourceAttribute() {
        return sourceAttribute;
    }

    /**
     * Get the {@link Attribute.PersistentAttributeType} for this association.
     * @return the persistent attribute type.
     */
    public Attribute.PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    /**
     * Check if the {@code sourceAttribute} is the owner of this association.
     * @return {@code true} if the {@code sourceAttribute} is the owner, {@code false} otherwise.
     */
    public boolean isOwner() {
        return owner;
    }

    /**
     * Gets the inverse for this association if any.
     * @return an {@link AssociationDescriptor} for the inverse of this association, or {@code null} if there is no inverse.
     */
    public AssociationDescriptor getInverse(){
        return this.associationInverseTargetAttributeType == null ? null
                : inverseTargetAttributeDescriptor.getAssociation(
                        associationInverseTargetAttributeType);
    }
}