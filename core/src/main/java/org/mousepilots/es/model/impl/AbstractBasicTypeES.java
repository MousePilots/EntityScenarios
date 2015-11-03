package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.BasicTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractBasicTypeES<T> implements BasicTypeES<T> {
    
    private final String javaClassName, typeName;
    private final int ordinal;
    
    public AbstractBasicTypeES(String javaClassName, String typeName, int ordinal){
        this.javaClassName = javaClassName;
        this.typeName = typeName;
        this.ordinal = ordinal;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isInstantiable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends Type<T>> getMetamodelClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TypeES<? super T>> getSuperTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TypeES<? extends T>> getSubTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistenceType getPersistenceType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<T> getJavaType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}