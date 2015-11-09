package org.mousepilots.es.model.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type of the represented object or attribute
 */
public class TypeESImpl<T> implements TypeES<T> {

    private final String javaClassName;
    private final String name;
    private final int ordinal;
    private final boolean instantiable;
    private final PersistenceType persistenceType;
    private final Class<T> javaType;
    private final Class<? extends Type<T>> metamodelClass;
    private final SortedSet<TypeES<? super T>> superTypes;
    private final SortedSet<TypeES<? extends T>> subTypes;

    public TypeESImpl(String javaClassName, String name, int ordinal, boolean instantiable, PersistenceType persistenceType, Class<T> javaType, Class<? extends Type<T>> metamodelClass, Collection<TypeES<? super T>> superTypes, Collection<TypeES<? extends T>> subTypes) {
        this.javaClassName = javaClassName;
        this.name = name;
        this.ordinal = ordinal;
        this.instantiable = instantiable;
        this.persistenceType = persistenceType;
        this.javaType = javaType;
        this.metamodelClass = metamodelClass;
        this.superTypes = new TreeSet<>(superTypes);
        this.subTypes = new TreeSet<>(subTypes);
    }

    @Override
    public String getJavaClassName() {
        return javaClassName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public boolean isInstantiable() {
        return instantiable;
    }

    @Override
    public T createInstance() {
        return (isInstantiable()) ? MetamodelUtilES.createInstance(getJavaType()) : null;
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
        return javaType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + this.ordinal;
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.ordinal != other.ordinal) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TypeES o) {
        return Integer.compare(ordinal, o.getOrdinal());
    }
}