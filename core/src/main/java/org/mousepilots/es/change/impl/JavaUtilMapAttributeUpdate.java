package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
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
public class JavaUtilMapAttributeUpdate<I extends Serializable, VE extends Serializable, K, V> extends AbstractIdentifiableUpdate<I, VE> implements HasAdditions<SimpleEntry<K, V>>, HasRemovals<SimpleEntry<K, V>> {

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
    public JavaUtilMapAttributeUpdate(AttributeES attribute, I id, VE version, Collection<SimpleEntry<K, V>> additions, Collection<SimpleEntry<K, V>> removals) {
        super(attribute, id, version);
        WrapperUtils.wrap(attribute, additions, this.additions, false);
        WrapperUtils.wrap(attribute, removals, this.removals, false);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
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