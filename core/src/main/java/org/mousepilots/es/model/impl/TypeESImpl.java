package org.mousepilots.es.model.impl;

import java.util.Objects;
import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 * @param <T> The type of the represented object or attribute
 */
public class TypeESImpl<T> implements TypeES<T> {

    private final String name;
    private final int ordinal;
    private final Class<T> javaType;
    private final Type.PersistenceType persistenceType;
    private final String javaClassName;
    private final boolean instantiable;
    private final Class<? extends Type<T>> metamodelClass;
    private final SortedSet<TypeES<? super T>> superTypes;
    private final SortedSet<TypeES<? extends T>> subTypes;

    public TypeESImpl(String name, int ordinal, Class<T> javaType, PersistenceType persistenceType, String javaClassName, boolean instantiable, Class<? extends Type<T>> metamodelClass, SortedSet<TypeES<? super T>> superTypes, SortedSet<TypeES<? extends T>> subTypes) {
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.persistenceType = persistenceType;
        this.javaClassName = javaClassName;
        this.instantiable = instantiable;
        this.metamodelClass = metamodelClass;
        this.superTypes = superTypes;
        this.subTypes = subTypes;
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
    public Class<T> getJavaType() {
        return javaType;
    }
@Override
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }
@Override
    public String getJavaClassName() {
        return javaClassName;
    }
@Override
    public boolean isInstantiable() {
        return instantiable;
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

    @Override
    public T createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}