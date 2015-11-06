package org.mousepilots.es.model.impl;

import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public class AssociationESImpl implements AssociationES {
    
    private final AttributeES sourceAttribute;
    private final PersistentAttributeType persistentAttributeType;
    private final AssociationES inverse;
    private final boolean owner;

    public AssociationESImpl(AttributeES sourceAttribute, PersistentAttributeType persistentAttributeType, AssociationES inverse, boolean owner) {
        if (sourceAttribute == null){
            throw new IllegalArgumentException("The source attribute cannot be null.");
        }
        this.sourceAttribute = sourceAttribute;
        this.persistentAttributeType = persistentAttributeType;
        this.inverse = inverse;
        this.owner = owner;
    }

    @Override
    public AssociationTypeES getAssociationType() {
        return sourceAttribute.isAssociation(AssociationTypeES.KEY) ? AssociationTypeES.KEY : AssociationTypeES.VALUE;
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
}