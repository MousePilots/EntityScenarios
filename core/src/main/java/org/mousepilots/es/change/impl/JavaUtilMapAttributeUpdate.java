package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
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
public abstract class JavaUtilMapAttributeUpdate<I extends Serializable, VE extends Serializable, K, V> extends AbstractIdentifiableUpdate<I, VE> implements HasAdditions<SimpleEntry<K, V>>, HasRemovals<SimpleEntry<K, V>> {

    private ArrayList<HasValue> additions;
    private ArrayList<HasValue> removals;

    public JavaUtilMapAttributeUpdate() {
        super();
    }

    /**
     *
     * @param attribute
     * @param version
     * @param id
     * @param additions
     * @param removals
     */
    public JavaUtilMapAttributeUpdate(AttributeES attribute, I id, VE version,
            Collection<SimpleEntry<K, V>> additions, Collection<SimpleEntry<K, V>> removals, DtoType dtoType) {
        super(attribute, id, version);
        WrapperUtils.wrapForChange(attribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(attribute, removals, this.removals, dtoType);
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