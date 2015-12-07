package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Roy Cleven
 * @param <C>
 * @param <U>
 * @param <A>
 */
public final class EmbeddableToEmbeddableSingularAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableSingularAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate() {
    }

    public EmbeddableToEmbeddableSingularAssociationAttributeUpdate(HasValue oldValue, HasValue newValue, C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(oldValue, newValue, container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}