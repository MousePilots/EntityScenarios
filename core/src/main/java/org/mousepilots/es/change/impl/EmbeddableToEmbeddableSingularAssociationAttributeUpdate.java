package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;

/**
 * @author Roy Cleven
 * @param <C>
 * @param <U>
 * @param <A>
 */
public final class EmbeddableToEmbeddableSingularAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableSingularAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate() {
    }

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute, A oldValue, A newValue) {
        super(container, containerAttribute, updated, updatedAttribute, oldValue, newValue);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}