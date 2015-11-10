package org.mousepilots.es.model.impl;

import java.util.Objects;
import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type of the represented object or attribute
 */
public class TypeESImpl<T> implements TypeES<T> {

    private final AttributeTypeParameters<T> attributeTypeParameters;
    private final PersistenceType persistenceType;
    private final String javaClassName;
    private final boolean instantiable;
    private final Class<? extends Type<T>> metamodelClass;
    private final SortedSet<TypeES<? super T>> superTypes;
    private final SortedSet<TypeES<? extends T>> subTypes;

    public TypeESImpl(AttributeTypeParameters<T> attributeTypeParameters,
            PersistenceType persistenceType, String javaClassName,
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

    @Override
    public String getJavaClassName() {
        return javaClassName;
    }

    @Override
    public String getName() {
        return attributeTypeParameters.getName();
    }

    @Override
    public int getOrdinal() {
        return attributeTypeParameters.getOrdinal();
    }

    @Override
    public boolean isInstantiable() {
        return instantiable;
    }

    @Override
    public T createInstance() {
        return (isInstantiable())
                ? MetamodelUtilES.createInstance(getJavaType()) : null;
    }

    @Override
    public Class<? extends Type<T>> getMetamodelClass() {
        return metamodelClass;
    }

    @Override
    public SortedSet<TypeES<? super T>> getSuperTypes() {
        return superTypes;
    }

    @Override
    public SortedSet<TypeES<? extends T>> getSubTypes() {
        return subTypes;
    }

    @Override
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    @Override
    public Class<T> getJavaType() {
        return attributeTypeParameters.getJavaType();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(getName());
        hash = 89 * hash + getOrdinal();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TypeESImpl<?> other = (TypeESImpl<?>) obj;
        if (!Objects.equals(getName(), other.getName())) {
            return false;
        }
        if (this.getOrdinal() != other.getOrdinal()) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TypeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }
}