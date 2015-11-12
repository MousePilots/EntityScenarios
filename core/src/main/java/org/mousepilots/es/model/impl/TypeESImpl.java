package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import java.util.Objects;
import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type of the represented object or attribute
 */
public class TypeESImpl<T> implements TypeES<T> {

    private final TypeParameters<T> typeParameters;

    public TypeESImpl(TypeParameters<T> typeParameters) {
        this.typeParameters = typeParameters;
        if (typeParameters == null
                || typeParameters.getAttributeTypeParameters() == null) {
            throw new IllegalArgumentException(
                "The type parameters or attributeTypeParameters cannot be null");
        }
    }

    @Override
    public String getJavaClassName() {
        return typeParameters.getJavaClassName();
    }

    @Override
    public String getName() {
        return typeParameters.getAttributeTypeParameters().getName();
    }

    @Override
    public int getOrdinal() {
        return typeParameters.getAttributeTypeParameters().getOrdinal();
    }

    @Override
    public boolean isInstantiable() {
        return typeParameters.isInstantiable();
    }

    @Override
    public T createInstance() {
        return (isInstantiable())
                ? MetamodelUtilES.createInstance(getJavaType()) : null;
    }

    @Override
    public Class<? extends Type<T>> getMetamodelClass() {
        return typeParameters.getMetamodelClass();
    }

    @Override
    public SortedSet<TypeES<? super T>> getSuperTypes() {
        return typeParameters.getSuperTypes();
    }

    @Override
    public SortedSet<TypeES<? extends T>> getSubTypes() {
        return typeParameters.getSubTypes();
    }

    @Override
    public PersistenceType getPersistenceType() {
        return typeParameters.getPersistenceType();
    }

    @Override
    public Class<T> getJavaType() {
        return typeParameters.getAttributeTypeParameters().getJavaType();
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
}