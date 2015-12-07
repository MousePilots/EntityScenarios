package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.change.HasAdditions;
import org.mousepilots.es.core.change.HasRemovals;
import org.mousepilots.es.core.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.WrapperUtils;

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