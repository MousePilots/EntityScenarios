package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.PluralAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractPluralAttributeES<T, C, E> implements PluralAttributeES<T, C, E> {

    private boolean readOnly;
    private boolean associated;
    private MemberES member;
    private String name;
    private PersistentAttributeType persistenceType;
    private Class<C> javaType;
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
    public Class<C> getJavaType() {
        return javaType;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}