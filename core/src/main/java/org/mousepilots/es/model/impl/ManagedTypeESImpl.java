package org.mousepilots.es.model.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 26-11-2015
 * @param <T> The represented type.
 */
public class ManagedTypeESImpl<T> extends TypeESImpl<T>
    implements ManagedTypeES<T> {

    private final Set<PluralAttribute<? super T, ?, ?>> pluralAttributes = new HashSet<>();
    private final Set<PluralAttribute<T, ?, ?>> declaredPluralAttributes = new HashSet<>();
    private final Set<Attribute<? super T, ?>> attributes = new HashSet<>();
    private final Set<Attribute<T, ?>> declaredAttributes = new HashSet<>();
    private final Set<SingularAttribute<? super T, ?>> singularAttributes;
    private final Set<SingularAttribute<T, ?>> declaredSingularAttributes;
    private final Set<CollectionAttributeES<? super T, ?>> collectionAttributes;
    private final Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes;
    private final Set<ListAttributeES<? super T, ?>> listAttributes;
    private final Set<ListAttributeES<T, ?>> declaredListAttributes;
    private final Set<SetAttributeES<? super T, ?>> setAttributes;
    private final Set<SetAttributeES<T, ?>> declaredSetAttributes;
    private final Set<MapAttributeES<? super T, ?, ?>> mapAttributes;
    private final Set<MapAttributeES<T, ?, ?>> declaredMapAttributes;

    /**
     * Create a new instance of this class.
     * @param singularAttributes the singular attributes that are part of this managed type.
     * @param declaredSingularAttributes the singular attributes that are declared by this managed type.
     * @param collectionAttributes the collection attributes that are part of this managed type.
     * @param declaredCollectionAttributes the collection attributes that are declared by this managed type.
     * @param listAttributes the list attributes that are part of this managed type.
     * @param declaredListAttributes the list attributes that are declared by this managed type.
     * @param setAttributes the set attributes that are part of this managed type.
     * @param declaredSetAttributes the set attributes that are declared by this managed type.
     * @param mapAttributes the map attributes that are part of this managed type.
     * @param declaredMapAttributes the map attributes that are declared by this managed type.
     * @param name the name of this managed type.
     * @param ordinal the ordinal of this managed type.
     * @param javaType the java type for this managed type.
     * @param persistenceType the {@link PersistenceType} for this managed type.
     * @param javaClassName the name of the java class that represents this managed type.
     * @param instantiable whether or not this managed type is instanciable.
     * @param metamodelClass the JPa meta model class for this managed type.
     * @param superTypes a set of super types for this managed type.
     * @param subTypes a set of sub types for this managed type.
     */
    public ManagedTypeESImpl(Set<SingularAttribute<? super T, ?>> singularAttributes,
            Set<SingularAttribute<T, ?>> declaredSingularAttributes,
            Set<CollectionAttributeES<? super T, ?>> collectionAttributes,
            Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes,
            Set<ListAttributeES<? super T, ?>> listAttributes,
            Set<ListAttributeES<T, ?>> declaredListAttributes,
            Set<SetAttributeES<? super T, ?>> setAttributes,
            Set<SetAttributeES<T, ?>> declaredSetAttributes,
            Set<MapAttributeES<? super T, ?, ?>> mapAttributes,
            Set<MapAttributeES<T, ?, ?>> declaredMapAttributes, String name,
            int ordinal, Class<T> javaType, PersistenceType persistenceType,
            String javaClassName, boolean instantiable,
            Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName,
                instantiable, metamodelClass, superTypes, subTypes);
        this.singularAttributes = singularAttributes;
        this.declaredSingularAttributes = declaredSingularAttributes;
        this.collectionAttributes = collectionAttributes;
        this.declaredCollectionAttributes = declaredCollectionAttributes;
        this.listAttributes = listAttributes;
        this.declaredListAttributes = declaredListAttributes;
        this.setAttributes = setAttributes;
        this.declaredSetAttributes = declaredSetAttributes;
        this.mapAttributes = mapAttributes;
        this.declaredMapAttributes = declaredMapAttributes;
        pluralAttributes.addAll(this.collectionAttributes);
        pluralAttributes.addAll(this.setAttributes);
        pluralAttributes.addAll(this.mapAttributes);
        pluralAttributes.addAll(this.listAttributes);
        declaredPluralAttributes.addAll(this.declaredCollectionAttributes);
        declaredPluralAttributes.addAll(this.declaredSetAttributes);
        declaredPluralAttributes.addAll(this.declaredMapAttributes);
        declaredPluralAttributes.addAll(this.declaredListAttributes);
        attributes.addAll(this.pluralAttributes);
        attributes.addAll(this.singularAttributes);
        declaredAttributes.addAll(this.declaredPluralAttributes);
        declaredAttributes.addAll(this.declaredSingularAttributes);
    }

    @Override
    public Set<Attribute<? super T, ?>> getAttributes() {
        return attributes;
    }

    @Override
    public Set<Attribute<T, ?>> getDeclaredAttributes() {
        return declaredAttributes;
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getSingularAttribute(
            String name, Class<Y> type) {
        Set<SingularAttribute<? super T, ?>> singularAttributeSet
                = this.singularAttributes;
        for (SingularAttribute<? super T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<? super T, Y>)att;
            }
        }
        return null;
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredSingularAttribute(
            String name, Class<Y> type) {
        Set<SingularAttribute<T, ?>> declaredSingularAttributeSet
                = this.declaredSingularAttributes;
        for (SingularAttribute<T, ?> att : declaredSingularAttributeSet) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<T, Y>)att;
            }
        }
        return null;
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getSingularAttributes() {
        return singularAttributes;
    }

    @Override
    public Set<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        return this.declaredSingularAttributes;
    }

    @Override
    public <E> CollectionAttributeES<? super T, E> getCollection(String name,
            Class<E> elementType) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributeSet
                = this.collectionAttributes;
        for (CollectionAttributeES<? super T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (CollectionAttributeES<? super T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <E> CollectionAttributeES<T, E> getDeclaredCollection(String name,
            Class<E> elementType) {
        Set<CollectionAttributeES<T, ?>> declaredCollectionAttributeSet
                = this.declaredCollectionAttributes;
        for (CollectionAttributeES<T, ?> att : declaredCollectionAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (CollectionAttributeES<T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <E> SetAttributeES<? super T, E> getSet(String name,
            Class<E> elementType) {
        Set<SetAttributeES<? super T, ?>> setAttributeSet
                = this.setAttributes;
        for (SetAttributeES<? super T, ?> att : setAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (SetAttributeES<? super T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <E> SetAttributeES<T, E> getDeclaredSet(String name,
            Class<E> elementType) {
        Set<SetAttributeES<T, ?>> declaredSetAttributeSet
                = this.declaredSetAttributes;
        for (SetAttributeES<T, ?> att : declaredSetAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (SetAttributeES<T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <E> ListAttributeES<? super T, E> getList(String name,
            Class<E> elementType) {
        Set<ListAttributeES<? super T, ?>> listAttributeSet
                = this.listAttributes;
        for (ListAttributeES<? super T, ?> att : listAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (ListAttributeES<? super T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <E> ListAttributeES<T, E> getDeclaredList(String name,
            Class<E> elementType) {
        Set<ListAttributeES<T, ?>> declaredListAttributeSet
                = this.declaredListAttributes;
        for (ListAttributeES<T, ?> att : declaredListAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (ListAttributeES<T, E>)att;
            }
        }
        return null;
    }

    @Override
    public <K, V> MapAttributeES<? super T, K, V> getMap(String name,
            Class<K> keyType, Class<V> valueType) {
        Set<MapAttributeES<? super T, ?, ?>> mapAttributeSet
                = this.mapAttributes;
        for (MapAttributeES<? super T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name) && keyType == att.getKeyJavaType()
                    && valueType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (MapAttributeES<? super T, K, V>)att;
            }
        }
        return null;
    }

    @Override
    public <K, V> MapAttributeES<T, K, V> getDeclaredMap(String name,
            Class<K> keyType, Class<V> valueType) {
        Set<MapAttributeES<T, ?, ?>> mapAttributeSet
                = this.declaredMapAttributes;
        for (MapAttributeES<T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name) && keyType == att.getKeyJavaType()
                    && valueType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (MapAttributeES<T, K, V>)att;
            }
        }
        return null;
    }

    @Override
    public Set<PluralAttribute<? super T, ?, ?>> getPluralAttributes() {
        return pluralAttributes;
    }

    @Override
    public Set<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes() {
        return declaredPluralAttributes;
    }

    @Override
    public Attribute<? super T, ?> getAttribute(String name) {
        Set<Attribute<? super T, ?>> attributeSet
                = this.attributes;
        for (Attribute<? super T, ?> att : attributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public Attribute<T, ?> getDeclaredAttribute(String name) {
        Set<Attribute<T, ?>> declaredAttributeSet
                = this.declaredAttributes;
        for (Attribute<T, ?> att : declaredAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<? super T, ?> getSingularAttribute(String name) {
        Set<SingularAttribute<? super T, ?>> singularAttributeSet
                = this.singularAttributes;
        for (SingularAttribute<? super T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<T, ?> getDeclaredSingularAttribute(String name) {
        Set<SingularAttribute<T, ?>> singularAttributeSet
                = this.declaredSingularAttributes;
        for (SingularAttribute<T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<? super T, ?> getCollection(String name) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributeSet
                = this.collectionAttributes;
        for (CollectionAttributeES<? super T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<T, ?> getDeclaredCollection(String name) {
        Set<CollectionAttributeES<T, ?>> collectionAttributeSet
                = this.declaredCollectionAttributes;
        for (CollectionAttributeES<T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<? super T, ?> getSet(String name) {
        Set<SetAttributeES<? super T, ?>> setAttributeSet
                = this.setAttributes;
        for (SetAttributeES<? super T, ?> att : setAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<T, ?> getDeclaredSet(String name) {
        Set<SetAttributeES<T, ?>> declaredSetAttributeSet
                = this.declaredSetAttributes;
        for (SetAttributeES<T, ?> att : declaredSetAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<? super T, ?> getList(String name) {
        Set<ListAttributeES<? super T, ?>> listAttributeSet
                = this.listAttributes;
        for (ListAttributeES<? super T, ?> att : listAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<T, ?> getDeclaredList(String name) {
        Set<ListAttributeES<T, ?>> declaredListAttributeSet
                = this.declaredListAttributes;
        for (ListAttributeES<T, ?> att : declaredListAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<? super T, ?, ?> getMap(String name) {
        Set<MapAttributeES<? super T, ?, ?>> mapAttributeSet
                = this.mapAttributes;
        for (MapAttributeES<? super T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<T, ?, ?> getDeclaredMap(String name) {
        Set<MapAttributeES<T, ?, ?>> declaredMapAttributeSet
                = this.declaredMapAttributes;
        for (MapAttributeES<T, ?, ?> att : declaredMapAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }
}