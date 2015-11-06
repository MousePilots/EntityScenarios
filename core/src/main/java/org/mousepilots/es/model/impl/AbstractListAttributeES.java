package org.mousepilots.es.model.impl;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractListAttributeES<T, E> implements ListAttributeES<T, E> {

    private final String attributeName;
    private final boolean isReadOnly;
    private final PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);
    private final Class<E> elementType;
    private final Class<Collection<E>> collectionType;

    public AbstractListAttributeES(String attributeName, boolean isReadOnly, PersistentAttributeType persistentAttributeType, MemberES javaMember, Class<E> elementType, Class<Collection<E>> collectionType) {
        this.attributeName = attributeName;
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
        return attributeName;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public ManagedTypeES<T> getDeclaringType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<List<E>> getJavaType() {
        return getJavaType();
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
        return CollectionType.LIST;
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
        return elementType;
    }    
}