package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SetAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractSetAttributeES<T, E> implements SetAttributeES<T, E> {

    private boolean readOnly;
    private boolean associated;
    private MemberES member;
    private String name;
    private PersistentAttributeType persistenceType;
    private Class<Set<E>> javaType;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);
    
    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return associations.containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return associations.get(type);
    }

    @Override
    public MemberES getJavaMember() {
        return member;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistenceType;
    }

    @Override
    public ManagedType<T> getDeclaringType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<Set<E>> getJavaType() {
        return javaType;
    }

    @Override
    public boolean isAssociation() {
        return associated;
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public CollectionType getCollectionType() {
        return PluralAttribute.CollectionType.SET;
    }

    @Override
    public Type<E> getElementType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BindableType getBindableType() {
        return BindableType.PLURAL_ATTRIBUTE;
    }

    @Override
    public Class<E> getBindableJavaType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.javaType);
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
        final AbstractSetAttributeES<?, ?> other = (AbstractSetAttributeES<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.javaType, other.javaType)) {
            return false;
        }
        return true;
    }
    
    
}