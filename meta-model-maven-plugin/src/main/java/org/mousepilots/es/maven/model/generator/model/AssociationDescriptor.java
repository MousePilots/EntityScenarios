package org.mousepilots.es.maven.model.generator.model;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.ManagedTypeDescriptor;
import org.mousepilots.es.core.model.AssociationTypeES;

/**
 * Descriptor that describes an association between two attributes.
 * @author Nicky Ernste
 * @version 1.0, 4-12-2015
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
        return this.inverseTargetAttributeDescriptor == null ? null
                : inverseTargetAttributeDescriptor.getAssociation(
                        associationInverseTargetAttributeType);
    }

    /**
     * Gets the target type for this association.
     * @return a {@link ManagedTypeDescriptor} that is the target of this association.
     */
    public ManagedTypeDescriptor getTargetTypeDescriptor() {
        return targetTypeDescriptor;
    }

    @Override
    public String toString() {
        String inverse = getInverse() != null
                ? getInverse().sourceAttribute.getName() : "null";
        StringBuilder sb = new StringBuilder("Source: ");
        sb.append(getSourceAttribute().getName()).append(", Attribute type: ")
                .append(getPersistentAttributeType()).append(", Target type: ")
                .append(getTargetTypeDescriptor().getName())
                .append(", Inverse association: ").append(inverse)
                .append(", Is owner: ").append(isOwner());
        return sb.toString();
    }
}