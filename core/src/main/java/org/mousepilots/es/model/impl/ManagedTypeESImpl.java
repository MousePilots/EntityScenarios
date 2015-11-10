package org.mousepilots.es.model.impl;

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
 * @version 1.0, 9-11-2015
 * @param <T> The represented type.
 */
public class ManagedTypeESImpl<T> extends TypeESImpl<T>
    implements ManagedTypeES<T> {

    public ManagedTypeESImpl(AttributeTypeParameters<T> attributeTypeParameters,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(attributeTypeParameters, persistenceType, javaClassName,
                instantiable, metamodelClass, superTypes, subTypes);
    }    

    @Override
    public Set<Attribute<? super T, ?>> getAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Attribute<T, ?>> getDeclaredAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <Y> SingularAttribute<? super T, Y> getSingularAttribute(
            String name, Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredSingularAttribute(
            String name, Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getSingularAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> CollectionAttribute<? super T, E> getCollection(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> CollectionAttribute<T, E> getDeclaredCollection(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> SetAttribute<? super T, E> getSet(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> SetAttribute<T, E> getDeclaredSet(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> ListAttribute<? super T, E> getList(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> ListAttribute<T, E> getDeclaredList(String name,
            Class<E> elementType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <K, V> MapAttribute<? super T, K, V> getMap(String name,
            Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <K, V> MapAttribute<T, K, V> getDeclaredMap(String name,
            Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<PluralAttribute<? super T, ?, ?>> getPluralAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Attribute<? super T, ?> getAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Attribute<T, ?> getDeclaredAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SingularAttribute<? super T, ?> getSingularAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SingularAttribute<T, ?> getDeclaredSingularAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CollectionAttribute<? super T, ?> getCollection(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CollectionAttribute<T, ?> getDeclaredCollection(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SetAttribute<? super T, ?> getSet(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SetAttribute<T, ?> getDeclaredSet(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListAttribute<? super T, ?> getList(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListAttribute<T, ?> getDeclaredList(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MapAttribute<? super T, ?, ?> getMap(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MapAttribute<T, ?, ?> getDeclaredMap(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}