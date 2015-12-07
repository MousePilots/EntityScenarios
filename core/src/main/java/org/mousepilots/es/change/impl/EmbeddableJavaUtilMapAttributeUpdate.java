package org.mousepilots.es.change.impl;

import java.util.List;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.model.impl.HasValueEntry;

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
