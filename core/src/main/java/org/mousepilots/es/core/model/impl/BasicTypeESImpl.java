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
     * @param metamodelClass the type's original JPA meta-model class
     * @param superTypeOrdinal the ordinal of {@code javaType}'s super-class' {@link Type}
     * @param subTypeOrdinals the ordinals of {@code javaType}'s sub-class' {@link Type}s
     * @param hasValueConstructor the value of hasValueConstructor
     */
    public BasicTypeESImpl(
         int ordinal, 
         Class<T> javaType, 
         Class<?> metamodelClass, 
         Integer superTypeOrdinal, 
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<T>> hasValueConstructor){
        super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor);
    }
    
    @Override
    public PersistenceType getPersistenceType(){
        return PersistenceType.BASIC;
    }
}