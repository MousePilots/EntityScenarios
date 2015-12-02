package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;

/**
 * @author Roy Cleven
 * @param <C>
 * @param <U>
 * @param <A>
 */
public final class EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate<C, U, A extends Serializable> extends EmbeddableJavaUtilCollectionAssociationAttributeUpdate<C, U, A> {

    public EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate() {
    }

    public EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute, Collection<A> additions, Collection<A> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute, additions, removals, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}