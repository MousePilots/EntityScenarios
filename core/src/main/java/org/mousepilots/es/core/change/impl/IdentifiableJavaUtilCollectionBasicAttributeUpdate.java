package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.change.HasAdditions;
import org.mousepilots.es.core.change.HasRemovals;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.util.WrapperUtils;

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

    public IdentifiableJavaUtilCollectionBasicAttributeUpdate(AttributeES attribute,
            V version, HasValue id, IdentifiableTypeES type, DtoType dtoType, Collection<Serializable> additions, Collection<Serializable> removals) {
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
