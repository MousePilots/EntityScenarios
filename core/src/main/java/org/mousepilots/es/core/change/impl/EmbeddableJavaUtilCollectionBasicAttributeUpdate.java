package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.change.ChangeVisitor;
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
public final class EmbeddableJavaUtilCollectionBasicAttributeUpdate<C, U, A extends Serializable> extends AbstractNonIdentifiableUpdate<C, U> implements HasAdditions<A>, HasRemovals<A> {

    private final Collection<HasValue> additions = new ArrayList<>();
    private final Collection<HasValue> removals = new ArrayList<>();

    private EmbeddableJavaUtilCollectionBasicAttributeUpdate() {
        super();
    }

    public EmbeddableJavaUtilCollectionBasicAttributeUpdate(C container, 
            U updated, HasValue containerId, AttributeES containerAttribute, 
            AttributeES updatedAttribute, TypeES type, DtoType dtoType, List<Serializable> additions, List<Serializable> removals) {
        super(container, updated, containerId, containerAttribute, updatedAttribute, type, dtoType, null);
        WrapperUtils.wrapForChange(updatedAttribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(updatedAttribute, removals, this.removals, dtoType);
    }

    @Override
    public List<A> getAdditions() {
        return WrapperUtils.unWrap(this.additions, new ArrayList<>());
    }

    @Override
    public List<A> getRemovals() {
        return WrapperUtils.unWrap(this.removals, new ArrayList<A>());
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}