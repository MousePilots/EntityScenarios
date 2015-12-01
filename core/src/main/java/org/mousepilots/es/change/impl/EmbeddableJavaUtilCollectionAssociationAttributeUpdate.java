package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.Dto;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author geenenju
 */
public abstract class EmbeddableJavaUtilCollectionAssociationAttributeUpdate<A extends Serializable> extends AbstractNonIdentifiableUpdate implements HasAdditions<A>, HasRemovals<A> {

    private ArrayList<HasValue> additions;
    private ArrayList<HasValue> removals;

    protected EmbeddableJavaUtilCollectionAssociationAttributeUpdate() {
        super();
    }

    public EmbeddableJavaUtilCollectionAssociationAttributeUpdate(Dto container,
            AttributeES containerAttribute, Dto updated, AttributeES updatedAttribute, Collection<A> additions,
            Collection<A> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute);
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
