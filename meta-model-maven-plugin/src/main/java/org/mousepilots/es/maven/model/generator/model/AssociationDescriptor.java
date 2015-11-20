package org.mousepilots.es.maven.model.generator.model;

import javax.persistence.metamodel.Attribute;
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
     * 
     * @param sourceAttribute
     * @param persistentAttributeType
     * @param owner
     * @param targetTypeDescriptor
     * @param inverseTargetAttributeDescriptor
     * @param associationInverseTargetAttributeType
     */
    public AssociationDescriptor(AttributeDescriptor sourceAttribute, Attribute.PersistentAttributeType persistentAttributeType, boolean owner, ManagedTypeDescriptor targetTypeDescriptor, AttributeDescriptor inverseTargetAttributeDescriptor, AssociationTypeES associationInverseTargetAttributeType) {
        this.sourceAttribute = sourceAttribute;
        this.persistentAttributeType = persistentAttributeType;
        this.owner = owner;
        this.targetTypeDescriptor = targetTypeDescriptor;
        this.inverseTargetAttributeDescriptor = inverseTargetAttributeDescriptor;
        this.associationInverseTargetAttributeType = associationInverseTargetAttributeType;
    }

    public AttributeDescriptor getSourceAttribute() {
        return sourceAttribute;
    }

    public Attribute.PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    public boolean isOwner() {
        return owner;
    }

    public AssociationDescriptor getInverse(){
        return this.associationInverseTargetAttributeType == null ? null
                : inverseTargetAttributeDescriptor.getAssociation(associationInverseTargetAttributeType);
    }
}