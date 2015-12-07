package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;

/**
 * @author Roy Cleven
 * @param <C>
 * @param <U>
 * @param <A>
 */
public final class EmbeddableToIdentifiableSingularAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableSingularAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToIdentifiableSingularAssociationAttributeUpdate() {
    }

    public EmbeddableToIdentifiableSingularAssociationAttributeUpdate(HasValue oldValue, HasValue newValue, C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(oldValue, newValue, container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}