package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;

/**
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends IdentifiableJavaUtilCollectionAssociationAttributeUpdate<I, V, A> {

    public IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate() {
    }

    public IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate(AttributeES attribute, I id, V version, Collection<A> additions, Collection<A> removals, DtoType dtoType) {
        super(attribute, id, version, additions, removals, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}