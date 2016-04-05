package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.HasValue;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The type for this BasicType.
 */
public class BasicTypeESImpl<T> extends TypeESImpl<T> implements BasicTypeES<T> {

    /**
     * @param ordinal the type's ordinal
     * @param javaType the {@link Type#getJavaType()}
     * @param superTypeOrdinal the ordinal of {@code javaType}'s super-class' {@link Type}
     * @param subTypeOrdinals the ordinals of {@code javaType}'s sub-class' {@link Type}s
     * @param hasValueConstructor the value of hasValueConstructor
     */
    public BasicTypeESImpl(
         int ordinal, 
         Class<T> javaType, 
         Integer superTypeOrdinal, 
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<? super T>> hasValueConstructor){
        super(ordinal, javaType, superTypeOrdinal, subTypeOrdinals, hasValueConstructor);
    }
    
    @Override
    public final PersistenceType getPersistenceType(){
        return PersistenceType.BASIC;
    }
}