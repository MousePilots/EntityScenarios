package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DTO;
import org.mousepilots.es.model.DtoType;

/**
 * @author Roy Cleven
 */
public final class EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate<A extends Serializable> extends EmbeddableJavaUtilCollectionAssociationAttributeUpdate<A> {
    
    public EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate() {
    }
    
    public EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate(DTO container, AttributeES containerAttribute, DTO updated, AttributeES updatedAttribute, Collection<A> additions, Collection<A> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute, additions, removals, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
    
}
