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

    public BasicTypeESImpl(
         int ordinal, 
         Class<T> javaType, 
         PersistenceType persistenceType, 
         Class<?> metamodelClass, 
         Integer superTypeOrdinal, 
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<T>> hasValueConstructor){
        super(ordinal, javaType, persistenceType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor);
    }

    
    
}