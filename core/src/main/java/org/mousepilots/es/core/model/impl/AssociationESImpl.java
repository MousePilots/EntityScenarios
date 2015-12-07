package org.mousepilots.es.core.model.impl;

import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import java.util.Objects;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 27-11-2015
 */
public class AssociationESImpl implements AssociationES {

    private final AttributeES sourceAttribute;
    private final PersistentAttributeType persistentAttributeType;
    private final AssociationES inverse;
    private final boolean owner;

    /**
     * Create a new instance of this class.
     * @param sourceAttribute the source attribute of this association.
     * @param persistentAttributeType the {@link PersistentAttributeType} for this association.
     * @param inverse the inverse of this association if it is a bidirectional association.
     * @param owner whether or not this side of the association is the owner.
     */
    public AssociationESImpl(AttributeES sourceAttribute,
            PersistentAttributeType persistentAttributeType,
            AssociationES inverse, boolean owner) {
        if (sourceAttribute == null){
            throw new IllegalArgumentException(
                    "The source attribute cannot be null.");
        }
        this.sourceAttribute = sourceAttribute;
        this.persistentAttributeType = persistentAttributeType;
        this.inverse = inverse;
        this.owner = owner;
    }

    @Override
    public AssociationTypeES getAssociationType() {
        return sourceAttribute.isAssociation(AssociationTypeES.KEY)
                ? AssociationTypeES.KEY : AssociationTypeES.VALUE;
    }

    @Override
    public AttributeES getSourceAttribute() {
        return sourceAttribute;
    }

    @Override
    public AssociationES getInverse() {
        return inverse;
    }

    @Override
    public boolean isOwner() {
        return owner;
    }

    @Override
    public boolean isBiDirectional() {
        return inverse != null;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType(){
        return persistentAttributeType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.sourceAttribute);
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
        final AssociationESImpl other = (AssociationESImpl) obj;
        return Objects.equals(this.sourceAttribute, other.sourceAttribute);
    }

    @Override
    public String toString() {
        return "Association source: " + getSourceAttribute().getName()
                + ", bidirectional: " + isBiDirectional() + ", owner: " + isOwner();
    }
}