package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type
 */
public class MappedSuperclassTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements MappedSuperclassTypeES<T> {

    /**
     * Create a new instance of this class.
     * @param id the attribute that forms the id of this mapped superclass type.
     * @param declaredId the attribute that is declared by this mapped superclass type and forms its id.
     * @param version the attribute that is the version of this mapped superclass type.
     * @param declaredVersion the attribute that is declared by this mapped superclass type and forms its version.
     * @param idClassAttributes a set of attributes that form the id of this mapped superclass type.
     * @param singleIdAttribute whether or not this mapped superclass type has a simple primary key or a composite primary key.
     * @param versionAttribute whether or not this mapped superclass type has a version attribute.
     * @param idType the {@link TypeES} that represents the id for this mapped superclass type.
     * @param name the name of this mapped superclass type.
     * @param ordinal the ordinal of this mapped superclass type.
     * @param javaType the java type for this mapped superclass type.
     * @param persistenceType the {@link PersistenceType} for this mapped superclass type.
     * @param javaClassName the name of the java class that represents this mapped superclass type.
     * @param instantiable whether or not this mapped superclass type is instanciable.
     * @param metamodelClass the JPa meta model class for this mapped superclass type.
     * @param attributes the attributes of this mapped super class.
     * @param superType the super type of this mapped superclass type.
     * @param subTypes a set of sub types for this mapped superclass type.
     */
    public MappedSuperclassTypeESImpl(
            int id, int declaredId, int version, int declaredVersion,
            Set<Integer> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute, int idType,
            String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<?> metamodelClass,
            Set<Integer> attributes, int superType,
            Collection<Integer> subTypes) {
        super(id, declaredId, version, declaredVersion, idClassAttributes,
                singleIdAttribute, versionAttribute, idType, name, ordinal,
                javaType, persistenceType, javaClassName, instantiable,
                metamodelClass, attributes, superType, subTypes);
    }
}