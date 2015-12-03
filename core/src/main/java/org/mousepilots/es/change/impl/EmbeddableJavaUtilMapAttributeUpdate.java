package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I> Id type
 * @param <VE> Version type
 * @param <K> Key type
 * @param <V> Value type
 */
public abstract class EmbeddableJavaUtilMapAttributeUpdate<C, U, K, V> extends AbstractNonIdentifiableUpdate<C, U> implements HasAdditions<SimpleEntry<K, V>>, HasRemovals<SimpleEntry<K, V>> {

    private ArrayList<HasValue> additions;
    private ArrayList<HasValue> removals;

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
    public EmbeddableJavaUtilMapAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute,
            Collection<SimpleEntry<K, V>> additions, Collection<SimpleEntry<K, V>> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute);
        WrapperUtils.wrapForChange(updatedAttribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(updatedAttribute, removals, this.removals, dtoType);
    }

    @Override
    public List<SimpleEntry<K, V>> getAdditions() {
        return WrapperUtils.unWrap(additions, new ArrayList<SimpleEntry<K, V>>());
    }

    @Override
    public List<SimpleEntry<K, V>> getRemovals() {
        return WrapperUtils.unWrap(removals, new ArrayList<SimpleEntry<K, V>>());
    }
}