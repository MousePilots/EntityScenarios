/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.DtoType;

/**
 * @author geenenju
 * @param <C>
 * @param <U>
 * @param <A>
 */
public final class EmbeddableSingularBasicAttributeUpdate<C, U, A extends Serializable> extends AbstractNonIdentifiableUpdate<C, U> {

    private HasValue oldValue;
    private HasValue newValue;

    protected EmbeddableSingularBasicAttributeUpdate() {
        super();
    }

    public EmbeddableSingularBasicAttributeUpdate(C container, AttributeES containerAttribute, U updated, AttributeES updatedAttribute, A oldValue, A newValue, DtoType dtoType) {
        super(container, containerAttribute, updated, updatedAttribute);
        this.oldValue = updatedAttribute.wrapForChange(oldValue, dtoType);
        this.newValue = updatedAttribute.wrapForChange(newValue, dtoType);
    }

    public A getOldValue() {
        return oldValue == null ? null : (A) oldValue.getValue();
    }

    public A getNewValue() {
        return newValue == null ? null : (A) newValue.getValue();
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}