package org.mousepilots.es.model.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractManagedTypeES<T> implements ManagedTypeES<T> {

    private final String javaClassName, name;
    private final int ordinal;
    private final PersistenceType persistanceType;
    private final Class<T> javaType;
    private final boolean isInstantiable;

    public AbstractManagedTypeES(String javaClassName, String name, int ordinal, PersistenceType persistanceType, Class<T> javaType, boolean isInstantiable) {
        this.javaClassName = javaClassName;
        this.name = name;
        this.ordinal = ordinal;
        this.persistanceType = persistanceType;
        this.javaType = javaType;
        this.isInstantiable = MetamodelUtilES.isInstantiable(javaType);
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
        return (isInstantiable()) ? MetamodelUtilES.createInstance(getJavaType()) : null;
    }

    @Override
    public Class<? extends Type<T>> getMetamodelClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SortedSet<TypeES<? super T>> getSuperTypes() {
        return null;
    }

    @Override
    public SortedSet<TypeES<? extends T>> getSubTypes() {
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
    public Set<Attribute<? super T, ?>> getAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Attribute<T, ?>> getDeclaredAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <Y> SingularAttribute<? super T, Y> getSingularAttribute(String name, Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredSingularAttribute(String name, Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getSingularAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> CollectionAttribute<? super T, E> getCollection(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> CollectionAttribute<T, E> getDeclaredCollection(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> SetAttribute<? super T, E> getSet(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> SetAttribute<T, E> getDeclaredSet(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> ListAttribute<? super T, E> getList(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> ListAttribute<T, E> getDeclaredList(String name, Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <K, V> MapAttribute<? super T, K, V> getMap(String name, Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <K, V> MapAttribute<T, K, V> getDeclaredMap(String name, Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<PluralAttribute<? super T, ?, ?>> getPluralAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attribute<? super T, ?> getAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attribute<T, ?> getDeclaredAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SingularAttribute<? super T, ?> getSingularAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SingularAttribute<T, ?> getDeclaredSingularAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionAttribute<? super T, ?> getCollection(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionAttribute<T, ?> getDeclaredCollection(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SetAttribute<? super T, ?> getSet(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SetAttribute<T, ?> getDeclaredSet(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListAttribute<? super T, ?> getList(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListAttribute<T, ?> getDeclaredList(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapAttribute<? super T, ?, ?> getMap(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapAttribute<T, ?, ?> getDeclaredMap(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + this.ordinal;
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
        final AbstractManagedTypeES<?> other = (AbstractManagedTypeES<?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.ordinal != other.ordinal) {
            return false;
        }
        return true;
    }
}