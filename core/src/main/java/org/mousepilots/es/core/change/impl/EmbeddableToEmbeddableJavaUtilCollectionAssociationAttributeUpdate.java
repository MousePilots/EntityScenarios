package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
public final class EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableJavaUtilCollectionAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate() {
    }

    public EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate(C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType, List<Serializable> additions, List<Serializable> removals) {
        super(container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType, additions, removals);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}