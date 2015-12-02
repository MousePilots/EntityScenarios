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
public final class EmbeddableToIdentifiableSingularAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableSingularAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToIdentifiableSingularAssociationAttributeUpdate() {
    }

    public EmbeddableToIdentifiableSingularAssociationAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute, A oldValue, A newValue) {
        super(container, containerAttribute, updated, updatedAttribute, oldValue, newValue);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}