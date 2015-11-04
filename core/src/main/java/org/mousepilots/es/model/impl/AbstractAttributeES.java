package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractAttributeES<T, TA> implements AttributeES<T, TA>{
    
    private final String name;
    private final boolean isReadOnly, isCollection;
    private final PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);
    private final Class<TA> javaType;
    
    public AbstractAttributeES(String name, boolean isReadOnly,
            boolean isCollection , PersistentAttributeType persistenAttributeType,
            MemberES javaMember, Class<TA> javaType){
        this.name = name;
        this.isReadOnly = isReadOnly;
        this.isCollection = isCollection;
        this.persistentAttributeType = persistenAttributeType;
        this.javaMember = javaMember;
        this.javaType = javaType;
    }

    @Override
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return this.associations.containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return this.associations.get(type);
    }

    @Override
    public MemberES getJavaMember() {
        //Maybe not allowed to get the member from the constructor?
        return javaMember;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return this.persistentAttributeType;
    }

    @Override
    public ManagedType<T> getDeclaringType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<TA> getJavaType() {
        return javaType;
    }

    @Override
    public boolean isAssociation() {
        //Not sure what to return here, since this is the method from the Attribute interface from JPA but we want people to use the other isAssociation method defined in the AtrributeES interface.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.javaType);
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
        final AbstractAttributeES<?, ?> other = (AbstractAttributeES<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.javaType, other.javaType)) {
            return false;
        }
        return true;
    }
}