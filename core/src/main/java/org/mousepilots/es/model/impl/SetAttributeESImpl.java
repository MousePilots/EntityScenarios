package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SetAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public class SetAttributeESImpl<T, E> implements SetAttributeES<T, E> {

    private final boolean readOnly;
    private final boolean associated;
    private final MemberES member;
    private final String name;
    private final PersistentAttributeType persistenceType;
    private final Class<Set<E>> javaType;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);

    public SetAttributeESImpl(boolean readOnly, boolean associated, MemberES member, String name, PersistentAttributeType persistenceType, Class<Set<E>> javaType) {
        this.readOnly = readOnly;
        this.associated = associated;
        this.member = member;
        this.name = name;
        this.persistenceType = persistenceType;
        this.javaType = javaType;
    }
    
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
    public ManagedTypeES<T> getDeclaringType() {
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
        final SetAttributeESImpl<?, ?> other = (SetAttributeESImpl<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.javaType, other.javaType)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(AttributeES o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOrdinal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}