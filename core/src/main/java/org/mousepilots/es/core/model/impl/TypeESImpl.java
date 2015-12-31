package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The type of the represented object or attribute
 */
public abstract class TypeESImpl<T> implements TypeES<T> {

    private final String name;
    private final int ordinal;
    private final Class<T> javaType;
    private final Type.PersistenceType persistenceType;
    private final String javaClassName;
    private final boolean instantiable;
    private final Class<?> metamodelClass;
    private final int superTypeOrdinal;
    private final SortedSet<Integer> subTypes;

    /**
     * Create a new instance of this class.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     * @param javaClassName the name of the java class for this type.
     * @param instantiable whether or not this type is instanciable.
     * @param metamodelClass the JPA metamodel class of this type.
     * @param superType a super type of this type.
     * @param subTypes a set of sub types of this type.
     */
    public TypeESImpl(
            String name,
            int ordinal,
            Class<T> javaType,
            PersistenceType persistenceType,
            String javaClassName,
            boolean instantiable,
            Class<?> metamodelClass,
            int superType, Collection<Integer> subTypes) {
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.persistenceType = persistenceType;
        this.javaClassName = javaClassName;
        this.instantiable = instantiable;
        this.metamodelClass = metamodelClass;
        this.superTypeOrdinal = superType;
        this.subTypes = new TreeSet<>(subTypes);
        AbstractMetaModelES.getInstance().register(this);
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
    public Class<?> getMetamodelClass() {
        return metamodelClass;
    }

    @Override
    public SortedSet<TypeES<? super T>> getSuperTypes() {
        //TODO Get mulitple super types.
        return null;
    }

    @Override
    public SortedSet<TypeES<? extends T>> getSubTypes() {
        SortedSet<TypeES<? extends T>> subs = new TreeSet<>();
        for (int subTypeOrdinal : subTypes) {
            subs.add(AbstractMetaModelES.getInstance().getType(subTypeOrdinal));
        }
        return subs;
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
        return this.getOrdinal() == other.getOrdinal();
    }

    @Override
    public int compareTo(TypeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    @Override
    public T createInstance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "Type name: " + getName() + ", ordinal: " + getOrdinal()
                + ", PersistenceType: " + getPersistenceType() + ", javaClass: "
                + getJavaClassName();
    }

    @Override
    public TypeES<? super T> getSuperType() {
        return AbstractMetaModelES.getInstance().getType(this.superTypeOrdinal);
    }
}