package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
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
     * @param metamodelClass the JPA metamodel class of this basic type.
     * @param subTypes a set of sub basic types of this basic type.
     */
    public BasicTypeESImpl(String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            TypeES<? super T> superType, Collection<TypeES<? extends T>> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName,
                instantiable, metamodelClass, superType, subTypes);
    }
}