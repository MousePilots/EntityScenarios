package org.mousepilots.es.change.impl;

import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.model.impl.HasValueEntry;

/**
 * @author Roy Cleven
 */
public final class EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate<C, U, K, V> extends EmbeddableJavaUtilMapAttributeUpdate<C, U, K, V> {

    public EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate() {
    }

    public EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate(List<HasValueEntry<K, V>> additions, List<HasValueEntry<K, V>> removals, C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(additions, removals, container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
