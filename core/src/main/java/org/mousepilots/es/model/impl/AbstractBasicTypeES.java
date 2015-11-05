package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.BasicTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 * @param <T> The type for this BasicType.
 */
public abstract class AbstractBasicTypeES<T> implements BasicTypeES<T> {
    
    private final String javaClassName, typeName;
    private final int ordinal;
    private final PersistenceType persistanceType;
    private final Class<T> javaType;
    private final boolean isInstantiable;
    
    public AbstractBasicTypeES(String javaClassName, String typeName, int ordinal, PersistenceType persistenceType, Class<T> javaType){
        this.javaClassName = javaClassName;
        this.typeName = typeName;
        this.ordinal = ordinal;
        this.persistanceType = persistenceType;
        this.javaType = javaType;
        this.isInstantiable = MetamodelUtilES.isInstantiable(javaType);
    }

    @Override
    public String getJavaClassName() {
        return javaClassName;
    }

    @Override
    public String getName() {
        return typeName;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public boolean isInstantiable() {
        return isInstantiable;
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
        //How to get the sub types?
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistenceType getPersistenceType() {
        return persistanceType;
    }

    @Override
    public Class<T> getJavaType() {
        return javaType;
    }    
}