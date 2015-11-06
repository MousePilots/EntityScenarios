package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
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
    
    private final ManagedTypeES declaringType;
    private final Class<TA> javaType;
    private final String attributeName;
    private final MemberES javaMember;
    private final PersistentAttributeType persistentAttributeType;
    private final int ordinal;
    private final boolean readOnly, collection, association;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);

    public AttributeESImpl(ManagedTypeES declaringType, Class<TA> javaType, String attributeName, MemberES javaMember, PersistentAttributeType persistentAttributeType, int ordinal, boolean readOnly, boolean collection, boolean association) {
        this.declaringType = declaringType;
        this.javaType = javaType;
        this.attributeName = attributeName;
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
        return this.attributeName;
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
}