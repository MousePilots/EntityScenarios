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
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableJavaUtilCollectionBasicAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends
        AbstractIdentifiableUpdate<I, V> implements HasAdditions<A>, HasRemovals<A> {

    private final ArrayList<HasValue> additions = new ArrayList<>();
    private final ArrayList<HasValue> removals = new ArrayList<>();

    private IdentifiableJavaUtilCollectionBasicAttributeUpdate() {
        super();
    }

    public IdentifiableJavaUtilCollectionBasicAttributeUpdate(AttributeES attribute, V version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(attribute, version, id, type, dtoType);
        WrapperUtils.wrapForChange(attribute, additions, this.additions, dtoType);
        WrapperUtils.wrapForChange(attribute, removals, this.removals, dtoType);
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
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
