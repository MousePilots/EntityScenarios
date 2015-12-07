package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The represented entity type.
 */
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements EntityTypeES<T>{

    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    /**
     * Create a new instance of this class.
     * @param bindableType the {@link BindableType} of this entity.
     * @param bindableJavaType the java type that is bound for this entity.
     * @param id the attribute that forms the id of this entity.
     * @param declaredId the attribute that is declared by this entity and forms its id.
     * @param version the attribute that is the version of this entity.
     * @param declaredVersion the attribute that is declared by this entity and forms its version.
     * @param superType the super type of this entity.
     * @param idClassAttributes a set of attributes that form the id of this entity.
     * @param singleIdAttribute whether or not this entity has a simple primary key or a composite primary key.
     * @param versionAttribute whether or not this entity has a version attribute.
     * @param idType the {@link TypeES} that represents the id for this entity.
     * @param name the name of this entity.
     * @param ordinal the ordinal of this entity.
     * @param javaType the java type for this entity.
     * @param persistenceType the {@link PersistenceType} for this entity.
     * @param javaClassName the name of the java class that represents this entity.
     * @param instantiable whether or not this entity is instanciable.
     * @param attributes the attributes this entity contains.
     * @param metamodelClass the JPa meta model class for this entity.
     * @param subTypes a set of sub types for this entity.
     */
    public EntityTypeESImpl(BindableType bindableType, Class<T> bindableJavaType,
            SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            Set<SingularAttribute<? super T, ?>> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute,
            TypeES<?> idType, String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            Set<Attribute<? super T, ?>> attributes, TypeES<? super T> superType,
            Collection<TypeES<? extends T>> subTypes) {
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