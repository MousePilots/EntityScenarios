package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.impl.classparameters.ManagedTypeParameters;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The represented type.
 */
public class ManagedTypeESImpl<T> extends TypeESImpl<T>
    implements ManagedTypeES<T> {

    private final ManagedTypeParameters<T> managedTypeParameters;
    private final Set<PluralAttribute<? super T, ?, ?>> pluralAttributes = new HashSet<>();
    private final Set<PluralAttribute<T, ?, ?>> declaredPluralAttributes = new HashSet<>();

    public ManagedTypeESImpl(ManagedTypeParameters<T> managedTypeParameters,
            TypeParameters<T> typeParameters) {
        super(typeParameters);
        this.managedTypeParameters = managedTypeParameters;
        pluralAttributes.addAll(this.managedTypeParameters.getCollectionAttributes());
        pluralAttributes.addAll(this.managedTypeParameters.getSetAttributes());
        pluralAttributes.addAll(this.managedTypeParameters.getMapAttributes());
        pluralAttributes.addAll(this.managedTypeParameters.getListAttributes());
        declaredPluralAttributes.addAll(this.managedTypeParameters.getDeclaredCollectionAttributes());
        declaredPluralAttributes.addAll(this.managedTypeParameters.getDeclaredSetAttributes());
        declaredPluralAttributes.addAll(this.managedTypeParameters.getDeclaredMapAttributes());
        declaredPluralAttributes.addAll(this.managedTypeParameters.getDeclaredListAttributes());
    }

    @Override
    public Set<Attribute<? super T, ?>> getAttributes() {
        return managedTypeParameters.getAttributes();
    }

    @Override
    public Set<Attribute<T, ?>> getDeclaredAttributes() {
        return managedTypeParameters.getDeclaredAttributes();
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getSingularAttribute(
            String name, Class<Y> type) {
        Set<SingularAttribute<? super T, ?>> singularAttributes
                = managedTypeParameters.getSingularAttributes();
        for (SingularAttribute<? super T, ?> att : singularAttributes) {
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
        Set<SingularAttribute<T, ?>> declaredSingularAttributes
                = managedTypeParameters.getDeclaredSingularAttributes();
        for (SingularAttribute<T, ?> att : declaredSingularAttributes) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<T, Y>)att;
            }
        }
        return null;
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getSingularAttributes() {
        return managedTypeParameters.getSingularAttributes();
    }

    @Override
    public Set<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        return managedTypeParameters.getDeclaredSingularAttributes();
    }

    @Override
    public <E> CollectionAttributeES<? super T, E> getCollection(String name,
            Class<E> elementType) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributes
                = managedTypeParameters.getCollectionAttributes();
        for (CollectionAttributeES<? super T, ?> att : collectionAttributes) {
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
        Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes
                = managedTypeParameters.getDeclaredCollectionAttributes();
        for (CollectionAttributeES<T, ?> att : declaredCollectionAttributes) {
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
        Set<SetAttributeES<? super T, ?>> setAttributes
                = managedTypeParameters.getSetAttributes();
        for (SetAttributeES<? super T, ?> att : setAttributes) {
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
        Set<SetAttributeES<T, ?>> declaredSetAttributes
                = managedTypeParameters.getDeclaredSetAttributes();
        for (SetAttributeES<T, ?> att : declaredSetAttributes) {
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
        Set<ListAttributeES<? super T, ?>> listAttributes
                = managedTypeParameters.getListAttributes();
        for (ListAttributeES<? super T, ?> att : listAttributes) {
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
        Set<ListAttributeES<T, ?>> declaredListAttributes
                = managedTypeParameters.getDeclaredListAttributes();
        for (ListAttributeES<T, ?> att : declaredListAttributes) {
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
        Set<MapAttributeES<? super T, ?, ?>> mapAttributes
                = managedTypeParameters.getMapAttributes();
        for (MapAttributeES<? super T, ?, ?> att : mapAttributes) {
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
        Set<MapAttributeES<T, ?, ?>> mapAttributes
                = managedTypeParameters.getDeclaredMapAttributes();
        for (MapAttributeES<T, ?, ?> att : mapAttributes) {
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
        Set<Attribute<? super T, ?>> attributes
                = managedTypeParameters.getAttributes();
        for (Attribute<? super T, ?> att : attributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public Attribute<T, ?> getDeclaredAttribute(String name) {
        Set<Attribute<T, ?>> declaredAttributes
                = managedTypeParameters.getDeclaredAttributes();
        for (Attribute<T, ?> att : declaredAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<? super T, ?> getSingularAttribute(String name) {
        Set<SingularAttribute<? super T, ?>> singularAttributes
                = managedTypeParameters.getSingularAttributes();
        for (SingularAttribute<? super T, ?> att : singularAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<T, ?> getDeclaredSingularAttribute(String name) {
        Set<SingularAttribute<T, ?>> singularAttributes
                = managedTypeParameters.getDeclaredSingularAttributes();
        for (SingularAttribute<T, ?> att : singularAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<? super T, ?> getCollection(String name) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributes
                = managedTypeParameters.getCollectionAttributes();
        for (CollectionAttributeES<? super T, ?> att : collectionAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<T, ?> getDeclaredCollection(String name) {
        Set<CollectionAttributeES<T, ?>> collectionAttributes
                = managedTypeParameters.getDeclaredCollectionAttributes();
        for (CollectionAttributeES<T, ?> att : collectionAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<? super T, ?> getSet(String name) {
        Set<SetAttributeES<? super T, ?>> setAttributes
                = managedTypeParameters.getSetAttributes();
        for (SetAttributeES<? super T, ?> att : setAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<T, ?> getDeclaredSet(String name) {
        Set<SetAttributeES<T, ?>> declaredSetAttributes
                = managedTypeParameters.getDeclaredSetAttributes();
        for (SetAttributeES<T, ?> att : declaredSetAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<? super T, ?> getList(String name) {
        Set<ListAttributeES<? super T, ?>> listAttributes
                = managedTypeParameters.getListAttributes();
        for (ListAttributeES<? super T, ?> att : listAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<T, ?> getDeclaredList(String name) {
        Set<ListAttributeES<T, ?>> declaredListAttributes
                = managedTypeParameters.getDeclaredListAttributes();
        for (ListAttributeES<T, ?> att : declaredListAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<? super T, ?, ?> getMap(String name) {
        Set<MapAttributeES<? super T, ?, ?>> mapAttributes
                = managedTypeParameters.getMapAttributes();
        for (MapAttributeES<? super T, ?, ?> att : mapAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<T, ?, ?> getDeclaredMap(String name) {
        Set<MapAttributeES<T, ?, ?>> declaredMapAttributes
                = managedTypeParameters.getDeclaredMapAttributes();
        for (MapAttributeES<T, ?, ?> att : declaredMapAttributes) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }
}