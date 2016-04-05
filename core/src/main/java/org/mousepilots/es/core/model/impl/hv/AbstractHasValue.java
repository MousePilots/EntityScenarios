/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.Objects;
import org.mousepilots.es.core.model.HasValue;

/**
 *
 * @author jgeenen
 * @param <T>
 */
public abstract class AbstractHasValue<T> implements HasValue<T>{

    @Override
    public boolean equals(Object obj){
        if(obj==null || getClass()!=obj.getClass()){
            return false;
        } else {
            final AbstractHasValue<T> other = (AbstractHasValue<T>) obj;
            return Objects.equals(this.getValue(), other.getValue());
        }
    }

    @Override
    public int hashCode(){
        final T value = getValue();
        return 5 + 7 * (value==null ? 0 : value.hashCode());
    }
}
