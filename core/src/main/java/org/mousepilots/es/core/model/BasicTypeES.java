package org.mousepilots.es.core.model;

import javax.persistence.metamodel.BasicType;

/**
 * Instances of the type {@link BasicTypeES} represent basic types (including temporal and enumerated types).
 * @param <T> The {@link  TypeES} of the {@code BasicTypeES}.
 * @see TypeES
 * @see BasicType
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface BasicTypeES<T> extends TypeES<T>,BasicType<T>{

    @Override
    public default <R,A> R accept(TypeVisitor<R,A> v, A arg){
        return v.visit(this, arg);
    }

    
}