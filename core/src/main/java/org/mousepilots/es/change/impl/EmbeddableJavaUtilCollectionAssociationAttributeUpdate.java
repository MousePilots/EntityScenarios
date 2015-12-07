package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author geenenju
 * @param <C>
 * @param <U>
 * @param <A>
 */
public abstract class EmbeddableJavaUtilCollectionAssociationAttributeUpdate<C, U, A extends Serializable> extends AbstractNonIdentifiableUpdate<C, U> implements HasAdditions<A>, HasRemovals<A> {

    private final ArrayList<HasValue> additions = new ArrayList<>();
    private final ArrayList<HasValue> removals = new ArrayList<>();

    protected EmbeddableJavaUtilCollectionAssociationAttributeUpdate() {
        super();
    }

    public EmbeddableJavaUtilCollectionAssociationAttributeUpdate(C container, 
            U updated, HasValue containerId, AttributeES containerAttribute,
            AttributeES updatedAttribute, TypeES type, DtoType dtoType) {
        super(container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType, null);
        WrapperUtils.wrapForChange(containerAttribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(containerAttribute, removals, this.removals, dtoType);
    }
    

    @Override
    public List<A> getAdditions() {
        return WrapperUtils.unWrap(this.additions, new ArrayList<A>());
    }

    @Override
    public ArrayList<A> getRemovals() {
        return WrapperUtils.unWrap(this.removals, new ArrayList<A>());
    }
}