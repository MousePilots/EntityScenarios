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
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I> the {@link Id}-type
 */
public abstract class AbstractIdentifiableChange<I extends Serializable> extends AbstractChange implements HasId<I> {

    private HasValue id;

    public AbstractIdentifiableChange() {
        super();
    }

    public AbstractIdentifiableChange(HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(type, dtoType);
        AttributeES idAttribute = type.getId(type.getIdType().getJavaType());
        if (idAttribute == null) {
            throw new IllegalArgumentException(type + " is not identifiable");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is a mandatory parameter");
        }
        this.id = idAttribute.wrapForChange(id,dtoType);
    }
    

    @Override
    public final I getId() {
        return id == null ? null : (I) id.getValue();
    }
}