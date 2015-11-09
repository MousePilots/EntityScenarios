package org.mousepilots.es.model.impl;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.BasicTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 * @param <T> The type for this BasicType.
 */
public abstract class AbstractBasicTypeES<T> implements BasicTypeES<T> {

    private final String javaClassName, name;
    private final int ordinal;
    private final PersistenceType persistanceType;
    private final Class<T> javaType;
    private final boolean isInstantiable;

    public AbstractBasicTypeES(String javaClassName, String name, int ordinal, PersistenceType persistenceType, Class<T> javaType, boolean isInstantiable){
        this.javaClassName = javaClassName;
        this.name = name;
        this.ordinal = ordinal;
        this.persistanceType = persistenceType;
        this.javaType = javaType;
        this.isInstantiable = isInstantiable;
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
        return isInstantiable;
    }

    @Override
    public T createInstance() {
        if (isInstantiable()) {
            try {
                return getJavaType().newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(AbstractBasicTypeES.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Class<? extends Type<T>> getMetamodelClass() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + this.ordinal;
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
        final AbstractBasicTypeES<?> other = (AbstractBasicTypeES<?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.ordinal != other.ordinal) {
            return false;
        }
        return true;
    }
}
