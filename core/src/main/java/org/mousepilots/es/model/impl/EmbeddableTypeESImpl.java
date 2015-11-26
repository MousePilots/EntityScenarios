package org.mousepilots.es.model.impl;

import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 26-11-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements EmbeddableTypeES<T> {

    /**
     * Create a new instance of this class.
     * @param singularAttributes the singular attributes that are part of this embeddable.
     * @param declaredSingularAttributes the singular attributes that are declared by this embeddable.
     * @param collectionAttributes the collection attributes that are part of this embeddable.
     * @param declaredCollectionAttributes the collection attributes that are declared by this embeddable.
     * @param listAttributes the list attributes that are part of this embeddable.
     * @param declaredListAttributes the list attributes that are declared by this embeddable.
     * @param setAttributes the set attributes that are part of this embeddable.
     * @param declaredSetAttributes the set attributes that are declared by this embeddable.
     * @param mapAttributes the map attributes that are part of this embeddable.
     * @param declaredMapAttributes the map attributes that are declared by this embeddable.
     * @param name the name of this embeddable.
     * @param ordinal the ordinal of this embeddable.
     * @param javaType the java type for this embeddable.
     * @param persistenceType the {@link PersistenceType} for this embeddable.
     * @param javaClassName the name of the java class that represents this embeddable.
     * @param instantiable whether or not this embeddable is instanciable.
     * @param metamodelClass the JPa meta model class for this embeddable.
     * @param superTypes a set of super types for this embeddable.
     * @param subTypes a set of sub types for this embeddable.
     */
    public EmbeddableTypeESImpl(Set<SingularAttribute<? super T, ?>> singularAttributes,
            Set<SingularAttribute<T, ?>> declaredSingularAttributes,
            Set<CollectionAttributeES<? super T, ?>> collectionAttributes,
            Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes,
            Set<ListAttributeES<? super T, ?>> listAttributes,
            Set<ListAttributeES<T, ?>> declaredListAttributes,
            Set<SetAttributeES<? super T, ?>> setAttributes,
            Set<SetAttributeES<T, ?>> declaredSetAttributes,
            Set<MapAttributeES<? super T, ?, ?>> mapAttributes,
            Set<MapAttributeES<T, ?, ?>> declaredMapAttributes,
            String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(singularAttributes, declaredSingularAttributes, collectionAttributes,
                declaredCollectionAttributes, listAttributes,
                declaredListAttributes, setAttributes, declaredSetAttributes,
                mapAttributes, declaredMapAttributes, name, ordinal, javaType,
                persistenceType, javaClassName, instantiable, metamodelClass,
                superTypes, subTypes);
    }
}