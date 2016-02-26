/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change.abst;

import java.io.Serializable;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public abstract class AbstractIdentifiableUpdate<I extends Serializable, V extends Serializable> extends AbstractIdentifiableVersionedChange<I, V> {

    private int attributeOrdinal;

    public AbstractIdentifiableUpdate() {
        super();
    }

    public AbstractIdentifiableUpdate(AttributeES attribute, V version, HasValue id, IdentifiableTypeES type) {
        super(version, id, type);
        this.attributeOrdinal = attribute.getOrdinal();
    }

    public final AttributeES getAttribute() {
        return AbstractMetamodelES.getInstance().getAttribute(attributeOrdinal);
    }

    @Override
    public final CRUD operation() {
        return CRUD.UPDATE;
    }
}