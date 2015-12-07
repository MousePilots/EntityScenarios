package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import org.mousepilots.es.core.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author geenenju
 * @param <C>
 * @param <U>
 * @param <A>
 */
public abstract class EmbeddableSingularAssociationAttributeUpdate<C, U, A extends Serializable> extends AbstractNonIdentifiableUpdate<C, U> {

    private HasValue oldValue;
    private HasValue newValue;

    protected EmbeddableSingularAssociationAttributeUpdate() {
        super();
    }

    public EmbeddableSingularAssociationAttributeUpdate(HasValue oldValue, 
            HasValue newValue, C container, U updated, HasValue containerId, 
            AttributeES containerAttribute, AttributeES updatedAttribute, 
            TypeES type, DtoType dtoType) {
        super(container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType, null);
        this.oldValue = containerAttribute.wrapForChange(oldValue, dtoType);
        this.newValue = containerAttribute.wrapForChange(newValue, dtoType);
    }


    public A getOldValue() {
        return oldValue == null ? null : (A) oldValue.getValue();
    }

    public A getNewValue() {
        return newValue == null ? null : (A) newValue.getValue();
    }
}