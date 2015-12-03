package org.mousepilots.es.change.impl;

import java.util.AbstractMap;
import java.util.Collection;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;

/**
 * @author Roy Cleven
 */
public final class EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate<C, U, K, V> extends EmbeddableJavaUtilMapAttributeUpdate<C, U, K, V> {

    public EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate() {
    }

    public EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute, Collection<AbstractMap.SimpleEntry<K, V>> additions, Collection<AbstractMap.SimpleEntry<K, V>> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute, additions, removals, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
