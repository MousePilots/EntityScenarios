package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;

/**
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableToEmbeddableSingularAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends IdentifiableSingularAssociationAttributeUpdate<I, V, A> {

    public IdentifiableToEmbeddableSingularAssociationAttributeUpdate() {
    }

    public IdentifiableToEmbeddableSingularAssociationAttributeUpdate(AttributeES attribute, V version, HasValue id, IdentifiableTypeES type, DtoType dtoType, Serializable oldValue, Serializable newValue) {
        super(attribute, version, id, type, dtoType, oldValue, newValue);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }   
}