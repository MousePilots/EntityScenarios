package org.mousepilots.es.model.impl;

import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.EntityTypeES;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 26-11-2015
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
     * @param singularAttributes the singular attributes that are part of this entity.
     * @param declaredSingularAttributes the singular attributes that are declared by this entity.
     * @param collectionAttributes the collection attributes that are part of this entity.
     * @param declaredCollectionAttributes the collection attributes that are declared by this entity.
     * @param listAttributes the list attributes that are part of this entity.
     * @param declaredListAttributes the list attributes that are declared by this entity.
     * @param setAttributes the set attributes that are part of this entity.
     * @param declaredSetAttributes the set attributes that are declared by this entity.
     * @param mapAttributes the map attributes that are part of this entity.
     * @param declaredMapAttributes the map attributes that are declared by this entity.
     * @param name the name of this entity.
     * @param ordinal the ordinal of this entity.
     * @param javaType the java type for this entity.
     * @param persistenceType the {@link PersistenceType} for this entity.
     * @param javaClassName the name of the java class that represents this entity.
     * @param instantiable whether or not this entity is instanciable.
     * @param metamodelClass the JPa meta model class for this entity.
     * @param superTypes a set of super types for this entity.
     * @param subTypes a set of sub types for this entity.
     */
    public EntityTypeESImpl(BindableType bindableType,
            Class<T> bindableJavaType, SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            IdentifiableTypeES<? super T> superType,
            Set<SingularAttribute<? super T, ?>> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType,
            Set<SingularAttribute<? super T, ?>> singularAttributes,
            Set<SingularAttribute<T, ?>> declaredSingularAttributes,
            Set<CollectionAttributeES<? super T, ?>> collectionAttributes,
            Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes,
            Set<ListAttributeES<? super T, ?>> listAttributes,
            Set<ListAttributeES<T, ?>> declaredListAttributes,
            Set<SetAttributeES<? super T, ?>> setAttributes,
            Set<SetAttributeES<T, ?>> declaredSetAttributes,
            Set<MapAttributeES<? super T, ?, ?>> mapAttributes,
            Set<MapAttributeES<T, ?, ?>> declaredMapAttributes, String name,
            int ordinal, Class<T> javaType, PersistenceType persistenceType,
            String javaClassName, boolean instantiable,
            Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(id, declaredId, version, declaredVersion, superType,
                idClassAttributes, singleIdAttribute, versionAttribute,
                idType, singularAttributes, declaredSingularAttributes,
                collectionAttributes, declaredCollectionAttributes, listAttributes,
                declaredListAttributes, setAttributes, declaredSetAttributes,
                mapAttributes, declaredMapAttributes, name, ordinal, javaType,
                persistenceType, javaClassName, instantiable, metamodelClass,
                superTypes, subTypes);
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