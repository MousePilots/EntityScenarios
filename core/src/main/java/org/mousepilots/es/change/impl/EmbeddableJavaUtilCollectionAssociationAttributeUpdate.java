/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.mousepilots.es.model.DTO;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.util.WrapperUtils;

/**
 * @author geenenju
 */
public final class EmbeddableJavaUtilCollectionAssociationAttributeUpdate<A extends Serializable> extends AbstractNonIdentifiableUpdate implements HasAdditions<A>, HasRemovals<A> {

    private ArrayList<HasValue> additions;
    private ArrayList<HasValue> removals;

    private EmbeddableJavaUtilCollectionAssociationAttributeUpdate() {
        super();
    }

    public EmbeddableJavaUtilCollectionAssociationAttributeUpdate(DTO container, AttributeES containerAttribute, DTO updated, AttributeES updatedAttribute, Collection<A> additions,
            Collection<A> removals) {
        super(container, containerAttribute, updated, updatedAttribute);
        WrapperUtils.wrap(containerAttribute, additions, this.additions, true);
        WrapperUtils.wrap(containerAttribute, removals, this.removals, true);
    }

    @Override
    public List<A> getAdditions() {
        return WrapperUtils.unWrap(this.additions, new ArrayList<A>());
    }

    @Override
    public ArrayList<A> getRemovals() {
        return WrapperUtils.unWrap(this.removals, new ArrayList<A>());
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
