package org.mousepilots.es.core.change.impl;

import java.util.List;
import org.mousepilots.es.core.change.HasAdditions;
import org.mousepilots.es.core.change.HasRemovals;
import org.mousepilots.es.core.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasValueEntry;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I> Id type
 * @param <VE> Version type
 * @param <K> Key type
 * @param <V> Value type
 */
public abstract class EmbeddableJavaUtilMapAttributeUpdate<C, U, K, V> extends AbstractNonIdentifiableUpdate<C, U> implements HasAdditions<HasValueEntry<K, V>>, HasRemovals<HasValueEntry<K, V>> {

    private List<HasValueEntry<K, V>> additions;
    private List<HasValueEntry<K, V>> removals;

    public EmbeddableJavaUtilMapAttributeUpdate() {
        super();
    }

    /**
     *
     * @param container
     * @param containerAttribute
     * @param additions
     * @param updated
     * @param updatedAttribute
     * @param removals
     * @param dtoType
     */
    public EmbeddableJavaUtilMapAttributeUpdate(List<HasValueEntry<K, V>> additions, List<HasValueEntry<K, V>> removals, C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType, null);
        this.additions = additions;
        this.removals = removals;
    }

    @Override
    public List<HasValueEntry<K, V>> getAdditions() {
        return additions;
    }

    @Override
    public List<HasValueEntry<K, V>> getRemovals() {
        return removals;
    }
}
