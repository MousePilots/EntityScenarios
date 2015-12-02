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
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.util.WrapperUtils;

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
            AttributeES containerAttribute, U updated, AttributeES updatedAttribute,
            Collection<A> additions, Collection<A> removals, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute);
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