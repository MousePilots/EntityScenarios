package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import java.util.Objects;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Jurjen van Geenen
 * @author Nicky Ernste
 * @version 1.0, 27-11-2015
 */
public final class AssociationESImpl implements AssociationES{

    public final int ordinal; 
    private final Integer inverseOrdinal;
    private final int sourceAttributeOrdinal, targetTypeOrdinal;
    private final PersistentAttributeType persistentAttributeType;
    private final AssociationTypeES associationType;
    private final boolean owner;

    public AssociationESImpl(
            int ordinal, 
            int sourceAttributeOrdinal,
            int targetTypeOrdinal,
            Integer inverseOrdinal,  
            boolean owner,
            AssociationTypeES associationType,
            PersistentAttributeType persistentAttributeType){
        this.ordinal = ordinal;
        this.sourceAttributeOrdinal = sourceAttributeOrdinal;
        this.targetTypeOrdinal = targetTypeOrdinal;
        this.inverseOrdinal = inverseOrdinal;
        this.persistentAttributeType = persistentAttributeType;
        this.associationType = associationType;
        this.owner = owner;
    }


    
    @Override
    public AssociationTypeES getAssociationType() {
         return associationType;
    }

    @Override
    public AttributeES getSourceAttribute() {
        return AbstractMetamodelES.getInstance().getAttribute(sourceAttributeOrdinal);
    }

    @Override
    public AssociationES getInverse(){
        return inverseOrdinal==null ? null : AbstractMetamodelES.getInstance().getAssociation(inverseOrdinal);
    }

    @Override
    public boolean isOwner() {
        return owner;
    }

    @Override
    public boolean isBiDirectional() {
        return inverseOrdinal!=null;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType(){
        return persistentAttributeType;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }
    
    @Override
    public ManagedTypeES getTargetType(){
         return (ManagedTypeES) AbstractMetamodelES.getInstance().getType(targetTypeOrdinal);
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.ordinal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public String toString() {
         return StringUtils.createToString(
              getClass(), 
              Arrays.asList(
                    "associationType",            getAssociationType(),
                    "persistentAttributeType",    persistentAttributeType,
                    "sourceAttribute",            getSourceAttribute(),
                    "targetType",                 getTargetType(),
                    "bidirectional",              inverseOrdinal!=null
              ));
    }
}