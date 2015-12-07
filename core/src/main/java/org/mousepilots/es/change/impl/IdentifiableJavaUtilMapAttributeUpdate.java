package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.impl.HasValueEntry;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I> Id type
 * @param <VE> Version type
 * @param <K> Key type
 * @param <V> Value type
 */
public abstract class IdentifiableJavaUtilMapAttributeUpdate<I extends Serializable, VE extends Serializable, K, V> extends AbstractIdentifiableUpdate<I, VE> implements HasAdditions<HasValueEntry<K, V>>, HasRemovals<HasValueEntry<K, V>> {

    private List<HasValueEntry<K,V>> additions;
    private List<HasValueEntry<K,V>> removals;

    public IdentifiableJavaUtilMapAttributeUpdate() {
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
    public IdentifiableJavaUtilMapAttributeUpdate(List<HasValueEntry<K, V>> additions, List<HasValueEntry<K, V>> removals, AttributeES attribute, VE version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(attribute, version, id, type, dtoType);
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