package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.EmbeddableTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements EmbeddableTypeES<T> {

    /**
     * Create a new instance of this class.
     * @param name the name of this embeddable.
     * @param ordinal the ordinal of this embeddable.
     * @param javaType the java type for this embeddable.
     * @param persistenceType the {@link PersistenceType} for this embeddable.
     * @param javaClassName the name of the java class that represents this embeddable.
     * @param instantiable whether or not this embeddable is instanciable.
     * @param metamodelClass the JPa meta model class for this embeddable.
     * @param attributes the attributes this embeddable contains.
     * @param superType the super type of this embeddable.
     * @param subTypes a set of sub types for this embeddable.
     */
    public EmbeddableTypeESImpl(String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<?> metamodelClass,
            Set<Integer> attributes, int superType,
            Collection<Integer> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName,
                instantiable, metamodelClass, attributes, superType, subTypes);
    }
}