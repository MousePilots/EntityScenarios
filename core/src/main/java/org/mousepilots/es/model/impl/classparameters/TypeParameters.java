package org.mousepilots.es.model.impl.classparameters;

import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * This class takes the most common type parameters and bundles them to
 * save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 */
public class TypeParameters<T> {

    private final AttributeTypeParameters<T> attributeTypeParameters;
    private final Type.PersistenceType persistenceType;
    private final String javaClassName;
    private final boolean instantiable;
    private final Class<? extends Type<T>> metamodelClass;
    private final SortedSet<TypeES<? super T>> superTypes;
    private final SortedSet<TypeES<? extends T>> subTypes;

    public TypeParameters(AttributeTypeParameters<T> attributeTypeParameters,
            Type.PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        this.attributeTypeParameters = attributeTypeParameters;
        this.persistenceType = persistenceType;
        this.javaClassName = javaClassName;
        this.instantiable = instantiable;
        this.metamodelClass = metamodelClass;
        this.superTypes = superTypes;
        this.subTypes = subTypes;
    }

    public AttributeTypeParameters<T> getAttributeTypeParameters() {
        return attributeTypeParameters;
    }

    public Type.PersistenceType getPersistenceType() {
        return persistenceType;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public boolean isInstantiable() {
        return instantiable;
    }

    public Class<? extends Type<T>> getMetamodelClass() {
        return metamodelClass;
    }

    public SortedSet<TypeES<? super T>> getSuperTypes() {
        return superTypes;
    }

    public SortedSet<TypeES<? extends T>> getSubTypes() {
        return subTypes;
    }
}