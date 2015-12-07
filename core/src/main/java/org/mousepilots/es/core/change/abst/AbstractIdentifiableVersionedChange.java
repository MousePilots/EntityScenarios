/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change.abst;

import java.io.Serializable;
import org.mousepilots.es.core.change.HasVersion;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.HasValue;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public abstract class AbstractIdentifiableVersionedChange<I extends Serializable, V extends Serializable> extends AbstractIdentifiableChange<I> implements HasVersion<V> {

    private HasValue version;
    public AbstractIdentifiableVersionedChange() {
        super();
    }

    public AbstractIdentifiableVersionedChange(HasValue version) {
        this.version = version;
    }

    public AbstractIdentifiableVersionedChange(V version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(id, type, dtoType);
        AttributeES va = type.getVersion(version.getClass());
        this.version = va.wrapForChange(version, dtoType);
    }

    @Override
    public final V getVersion() {
        return version == null ? null : (V) version.getValue();
    }
}