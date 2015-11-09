package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public class AttributeESImpl<T, TA> implements AttributeES<T, TA>{
    
    private final String name;
    private final boolean isReadOnly, isCollection;
    private final PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final PersistentAttributeType persistentAttributeType;
    private final int ordinal;
    private final boolean readOnly, collection, association;
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
        this.persistentAttributeType = persistentAttributeType;
        this.ordinal = ordinal;
        this.readOnly = readOnly;
        this.collection = collection;
        this.association = association;
    }
    
    @Override
    public boolean isReadOnly() {
        return this.readOnly;
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
    public ManagedTypeES<T> getDeclaringType() {
        return declaringType;
    }

    @Override
    public Class<TA> getJavaType() {
        return javaType;
    }

    @Override
    public boolean isAssociation() {
        return association;
    }

    @Override
    public boolean isCollection() {
        return collection;
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(ordinal, o.getOrdinal());
    }

    @Override
    public int getOrdinal() {
        return ordinal;
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
