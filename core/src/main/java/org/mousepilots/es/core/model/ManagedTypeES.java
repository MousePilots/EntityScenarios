package org.mousepilots.es.core.model;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Instances of the type {@link ManagedTypeES} represent entity, mapped
 * superclass, and embeddable types.
 *
 * @param <T> The represented type.
 * @see TypeES
 * @see ManagedType
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface ManagedTypeES<T> extends TypeES<T>, ManagedType<T> {

    @Override
    <Y> SingularAttributeES<? super T, Y> getSingularAttribute(String name, Class<Y> type);

    @Override
    <Y> SingularAttributeES<T, Y> getDeclaredSingularAttribute(
            String name, Class<Y> type);

    @Override
    <E> CollectionAttributeES<? super T, E> getCollection(String name,
            Class<E> elementType);

    @Override
    <E> CollectionAttributeES<T, E> getDeclaredCollection(String name,
            Class<E> elementType);

    @Override
    <E> SetAttributeES<? super T, E> getSet(String name, Class<E> elementType);

    @Override
    <E> SetAttributeES<T, E> getDeclaredSet(String name, Class<E> elementType);

    @Override
    <E> ListAttributeES<? super T, E> getList(String name,
            Class<E> elementType);

    @Override
    <E> ListAttributeES<T, E> getDeclaredList(String name,
            Class<E> elementType);

    @Override
    <K, V> MapAttributeES<? super T, K, V> getMap(String name,
            Class<K> keyType, Class<V> valueType);

    @Override
    <K, V> MapAttributeES<T, K, V> getDeclaredMap(String name,
            Class<K> keyType, Class<V> valueType);

    @Override
    Attribute<? super T, ?> getAttribute(String name);

    @Override
    Attribute<T, ?> getDeclaredAttribute(String name);

    @Override
    SingularAttribute<? super T, ?> getSingularAttribute(String name);

    @Override
    SingularAttribute<T, ?> getDeclaredSingularAttribute(String name);

    @Override
    CollectionAttributeES<? super T, ?> getCollection(String name);

    @Override
    CollectionAttributeES<T, ?> getDeclaredCollection(String name);

    @Override
    SetAttributeES<? super T, ?> getSet(String name);

    @Override
    SetAttributeES<T, ?> getDeclaredSet(String name);

    @Override
    ListAttributeES<? super T, ?> getList(String name);

    @Override
    ListAttributeES<T, ?> getDeclaredList(String name);

    @Override
    MapAttributeES<? super T, ?, ?> getMap(String name);

    @Override
    MapAttributeES<T, ?, ?> getDeclaredMap(String name);
}
