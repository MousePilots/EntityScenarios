package org.mousepilots.es.core.change.impl;

import java.util.List;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasValueEntry;

/**
 * @author Roy Cleven
 */
public final class EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate<C, U, K, V> extends EmbeddableJavaUtilMapAttributeUpdate<C, U, K, V> {

    public EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate() {
    }

    public EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate(List<HasValueEntry<K, V>> additions, List<HasValueEntry<K, V>> removals, C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(additions, removals, container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType);
    }
    
    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
