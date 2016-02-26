package org.mousepilots.es.core.change.impl;

import java.io.Serializable;

import javax.persistence.Version;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableVersionedChange;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public final class Delete<I extends Serializable, V extends Serializable> extends AbstractIdentifiableVersionedChange<I, V> {

    protected Delete() {
        super();
    }

    /**
     * Constructor: create a new Delete.
     *
     * @param type type of the new object to create
     * @param id id of the new object to create
     * @param version the source {@link Version}, may be {@code null}
     */
    public Delete(V version, HasValue id, IdentifiableTypeES type) {
        super(version, id, type);
    }

    @Override
    public CRUD operation() {
        return CRUD.DELETE;
    }

//    @Override
//    public void accept(ChangeVisitor changeHandler) {
//        changeHandler.visit(this);
//    }
}
