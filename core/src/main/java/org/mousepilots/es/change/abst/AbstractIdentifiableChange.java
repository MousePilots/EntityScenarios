/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.abst;

import java.io.Serializable;

import javax.persistence.Id;
import org.mousepilots.es.change.HasId;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.Wrapper;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I> the {@link Id}-type
 */
public abstract class AbstractIdentifiableChange<I extends Serializable> extends AbstractChange implements HasId<I> {

    private Wrapper id;

    public AbstractIdentifiableChange() {
        super();
    }

    public AbstractIdentifiableChange(IdentifiableTypeES type, I id) {
        super(type);
        AttributeES idAttribute = type.getId(type.getIdType().getJavaType());
        if (idAttribute == null) {
            throw new IllegalArgumentException(type + " is not identifiable");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is a mandatory parameter");
        }
    }

    @Override
    public final I getId() {
        return id == null ? null : (I) id.unwrap();
    }
}