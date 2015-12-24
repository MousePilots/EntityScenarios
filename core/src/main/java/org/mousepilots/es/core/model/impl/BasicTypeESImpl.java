package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import org.mousepilots.es.core.model.BasicTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The type for this BasicType.
 */
public class BasicTypeESImpl<T> extends TypeESImpl<T>
    implements BasicTypeES<T> {

    /**
     * Create a new instance of this class.
     * @param name the name of this basic type.
     * @param ordinal the ordinal of this basic type.
     * @param javaType the java basic type of this basic type.
     * @param persistenceType the {@link PersistenceType} of this basic type.
     * @param javaClassName the name of the java class for this basic type.
     * @param instantiable whether or not this basic type is instanciable.
     * @param metamodelClass the JPA meta-model class of this basic type.
     * @param superType the super type of this basic type if any.
     * @param subTypes a set of sub basic types of this basic type.
     */
    public BasicTypeESImpl(String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<?> metamodelClass,
            int superType, Collection<Integer> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName,
                instantiable, metamodelClass, superType, subTypes);
    }
}