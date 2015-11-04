package org.mousepilots.es.model.impl;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractCollectionAttributeES<T, E> implements CollectionAttributeES<T, E> {

    private final String name;
    private final boolean isReadOnly;
    private final PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);
    private final Class<E> elementType;
    private final Class<Collection<E>> collectionType;

    public AbstractCollectionAttributeES(String name, boolean isReadOnly, PersistentAttributeType persistentAttributeType, MemberES javaMember, Class<E> elementType, Class<Collection<E>> collectionType) {
        this.name = name;
        this.isReadOnly = isReadOnly;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.elementType = elementType;
        this.collectionType = collectionType;
    }    
    
    @Override
    public boolean isReadOnly() {
        return isReadOnly;
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
        return javaMember;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public ManagedType<T> getDeclaringType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<Collection<E>> getJavaType() {
        return collectionType;
    }

    @Override
    public boolean isAssociation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.COLLECTION;
    }

    @Override
    public Type<E> getElementType() {
        //TODO Return elementType as a Type<E>.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BindableType getBindableType() {
        return BindableType.PLURAL_ATTRIBUTE;
    }

    @Override
    public Class<E> getBindableJavaType() {
        return elementType;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.elementType);
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
        final AbstractCollectionAttributeES<?, ?> other = (AbstractCollectionAttributeES<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.elementType, other.elementType)) {
            return false;
        }
        return true;
    }
}