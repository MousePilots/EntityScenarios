package org.mousepilots.es.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 * @param <T>
 */
public abstract class AbstractTypeES<T> implements TypeES<T> {

    private final String javaClassName;
    private final String name;
    private final int ordinal;
    private final boolean instantiable;
    private final Type.PersistenceType persistenceType;
    private final Class<T> javaType;

    protected AbstractTypeES(String javaClassName, String name, int ordinal, PersistenceType persistenceType, Class<T> javaType) {
        this.javaClassName = javaClassName;
        this.name = name;
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.instantiable = MetamodelUtilES.isInstantiable(this.javaType);
        this.persistenceType = persistenceType;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TypeES<? super T>> getSuperTypes() {
        return MetamodelUtilES.getSuperTypes(getJavaType());
    }

    @Override
    public Collection<TypeES<? extends T>> getSubTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    @Override
    public Class<T> getJavaType() {
        return javaType;
    }
}
