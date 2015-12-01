package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.Dto;

/**
 * @author Roy Cleven
 */
public final class EmbeddableToEmbeddableSingularAssociationAttributeUpdate<A extends Serializable> extends EmbeddableSingularAssociationAttributeUpdate<A> {

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate() {
    }

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate(Dto container, AttributeES containerAttribute, Dto updated, AttributeES updatedAttribute, A oldValue, A newValue) {
        super(container, containerAttribute, updated, updatedAttribute, oldValue, newValue);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
