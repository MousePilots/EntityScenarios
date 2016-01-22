package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * This class implements the EntityTypeES interface.
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type.
 */
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements EntityTypeES<T>{

    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    //@TODO add declared attributes to constructor
    /**
     * Create a new instance of this class.
     * @param bindableType the {@link BindableType} of this entity.
     * @param bindableJavaType the java type that is bound for this entity.
     * @param id the attribute that forms the id of this entity.
     * @param declaredId the attribute that is declared by this entity and forms its id.
     * @param version the attribute that is the version of this entity.
     * @param declaredVersion the attribute that is declared by this entity and forms its version.
     * @param idClassAttributes a set of attributes that form the id of this entity.
     * @param singleIdAttribute whether or not this entity has a simple primary key or a composite primary key.
     * @param versionAttribute whether or not this entity has a version attribute.
     * @param idType the {@link TypeES} that represents the id for this entity.
     * @param name the name of this entity.
     * @param ordinal the ordinal of this entity.
     * @param javaType the java type for this entity.
     * @param persistenceType the {@link PersistenceType} for this entity.
     * @param javaClassName the name of the java class that represents this entity.
     * @param instantiable whether or not this entity is instantiable.
     * @param metamodelClass the JPa meta model class for this entity.
     * @param attributes the attributes this entity contains.
     * @param superType the super type of this entity.
     * @param subTypes a set of sub types for this entity.
     */
    public EntityTypeESImpl(BindableType bindableType, Class<T> bindableJavaType,
            int id, int declaredId, int version, int declaredVersion,
            Set<Integer> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute,
            int idType, String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<?> metamodelClass,
            Set<Integer> attributes, int superType,
            Collection<Integer> subTypes) {
        super(id, declaredId, version, declaredVersion, idClassAttributes,
                singleIdAttribute, versionAttribute, idType, name, ordinal,
                javaType, persistenceType, javaClassName, instantiable,
                metamodelClass, attributes, superType, subTypes);
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    @Override
    public BindableType getBindableType() {
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableJavaType;
    }
}