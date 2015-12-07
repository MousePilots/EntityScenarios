package org.mousepilots.es.core.model.impl;

import java.util.Objects;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 */
public abstract class AbstractHasValueImpl<T> implements HasValue<T> {

    private T value;
    private DtoType dtoType;
    
    @Override
    public final T getValue() {
        return value;
    }

    @Override
    public final void setValue(T value) {
        this.value = value;
    }

    @Override
    public DtoType getDtoType() {
        return dtoType;
    }

    @Override
    public void setDtoType(DtoType dtoType) {
        this.dtoType = dtoType;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractHasValueImpl<?> other = (AbstractHasValueImpl<?>) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
}
