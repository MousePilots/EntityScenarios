package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author geenenju
 */
public abstract class IdentifiableJavaUtilCollectionAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends AbstractIdentifiableUpdate<I, V> implements HasAdditions<A>,
        HasRemovals<A> {

    private ArrayList<HasValue> additions;
    private ArrayList<HasValue> removals;

    protected IdentifiableJavaUtilCollectionAssociationAttributeUpdate() {
        super();
    }

    public IdentifiableJavaUtilCollectionAssociationAttributeUpdate(AttributeES attribute,
            I id, V version, Collection<A> additions, Collection<A> removals, DtoType dtoType) {
        super(attribute, id, version);
        WrapperUtils.wrapForChange(attribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(attribute, removals, this.removals, dtoType);
    }

    @Override
    public List<A> getAdditions() {
        return WrapperUtils.unWrap(this.additions, new ArrayList<A>());
    }

    @Override
    public List<A> getRemovals() {
        return WrapperUtils.unWrap(this.removals, new ArrayList<A>());
    }
}
