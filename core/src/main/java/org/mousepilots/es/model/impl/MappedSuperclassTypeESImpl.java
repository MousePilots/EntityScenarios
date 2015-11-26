package org.mousepilots.es.model.impl;

import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.MappedSuperclassTypeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 26-11-2015
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
     * @param superType the super type of this mapped superclass type.
     * @param idClassAttributes a set of attributes that form the id of this mapped superclass type.
     * @param singleIdAttribute whether or not this mapped superclass type has a simple primary key or a composite primary key.
     * @param versionAttribute whether or not this mapped superclass type has a version attribute.
     * @param idType the {@link TypeES} that represents the id for this mapped superclass type.
     * @param singularAttributes the singular attributes that are part of this mapped superclass type.
     * @param declaredSingularAttributes the singular attributes that are declared by this mapped superclass type.
     * @param collectionAttributes the collection attributes that are part of this mapped superclass type.
     * @param declaredCollectionAttributes the collection attributes that are declared by this mapped superclass type.
     * @param listAttributes the list attributes that are part of this mapped superclass type.
     * @param declaredListAttributes the list attributes that are declared by this mapped superclass type.
     * @param setAttributes the set attributes that are part of this mapped superclass type.
     * @param declaredSetAttributes the set attributes that are declared by this mapped superclass type.
     * @param mapAttributes the map attributes that are part of this mapped superclass type.
     * @param declaredMapAttributes the map attributes that are declared by this mapped superclass type.
     * @param name the name of this mapped superclass type.
     * @param ordinal the ordinal of this mapped superclass type.
     * @param javaType the java type for this mapped superclass type.
     * @param persistenceType the {@link PersistenceType} for this mapped superclass type.
     * @param javaClassName the name of the java class that represents this mapped superclass type.
     * @param instantiable whether or not this mapped superclass type is instanciable.
     * @param metamodelClass the JPa meta model class for this mapped superclass type.
     * @param superTypes a set of super types for this mapped superclass type.
     * @param subTypes a set of sub types for this mapped superclass type.
     */
    public MappedSuperclassTypeESImpl(SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            IdentifiableTypeES<? super T> superType,
            Set<SingularAttribute<? super T, ?>> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute,
            TypeES<?> idType, Set<SingularAttribute<? super T, ?>> singularAttributes,
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
                idClassAttributes, singleIdAttribute, versionAttribute, idType,
                singularAttributes, declaredSingularAttributes, collectionAttributes,
                declaredCollectionAttributes, listAttributes,
                declaredListAttributes, setAttributes, declaredSetAttributes,
                mapAttributes, declaredMapAttributes, name, ordinal, javaType,
                persistenceType, javaClassName, instantiable, metamodelClass,
                superTypes, subTypes);
    }
}