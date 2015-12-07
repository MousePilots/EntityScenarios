/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.abst;

import java.io.Serializable;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public abstract class AbstractIdentifiableUpdate<I extends Serializable, V extends Serializable> extends AbstractIdentifiableVersionedChange<I, V> {

    private int attributeOrdinal;
    private AttributeES attribute;

    public AbstractIdentifiableUpdate() {
        super();
    }

    public AbstractIdentifiableUpdate(AttributeES attribute, V version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(version, id, type, dtoType);
        this.attribute = attribute;
        this.attributeOrdinal = attribute.getOrdinal();
    }

    public final AttributeES getAttribute() {
        return attribute;
    }

    @Override
    public final CRUD operation() {
        return CRUD.UPDATE;
    }
}