package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableChange;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 */
public final class Create<I extends Serializable> extends AbstractIdentifiableChange<I> {

    protected Create() {
        super();
    }

    /**
     * Constructor: create a new Create.
     *
     * @param type type of the new object to create
     * @param id id of the new object to create
     * @param dtoType
     */
    public Create(HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(id, type, dtoType);
    }

    @Override
    public CRUD operation() {
        return CRUD.CREATE;
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}