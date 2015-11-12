package org.mousepilots.es.model.impl.classparameters;

import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;

/**
 * This class takes the most common managed type parameters and bundles
 * them to save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T>
 */
public class ManagedTypeParameters<T> {

    private final Set<Attribute<? super T, ?>> attributes;
    private final Set<Attribute<T, ?>> declaredAttributes;
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

    public ManagedTypeParameters(Set<Attribute<? super T, ?>> attributes,
            Set<Attribute<T, ?>> declaredAttributes,
            Set<SingularAttribute<? super T, ?>> singularAttributes,
            Set<SingularAttribute<T, ?>> declaredSingularAttributes,
            Set<CollectionAttributeES<? super T, ?>> collectionAttributes,
            Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes,
            Set<ListAttributeES<? super T, ?>> listAttributes,
            Set<ListAttributeES<T, ?>> declaredListAttributes,
            Set<SetAttributeES<? super T, ?>> setAttributes,
            Set<SetAttributeES<T, ?>> declaredSetAttributes,
            Set<MapAttributeES<? super T, ?, ?>> mapAttributes,
            Set<MapAttributeES<T, ?, ?>> declaredMapAttributes) {
        this.attributes = attributes;
        this.declaredAttributes = declaredAttributes;
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
    }

    public Set<Attribute<? super T, ?>> getAttributes() {
        return attributes;
    }

    public Set<Attribute<T, ?>> getDeclaredAttributes() {
        return declaredAttributes;
    }

    public Set<SingularAttribute<? super T, ?>> getSingularAttributes() {
        return singularAttributes;
    }

    public Set<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        return declaredSingularAttributes;
    }

    public Set<CollectionAttributeES<? super T, ?>> getCollectionAttributes() {
        return collectionAttributes;
    }

    public Set<CollectionAttributeES<T, ?>> getDeclaredCollectionAttributes() {
        return declaredCollectionAttributes;
    }

    public Set<ListAttributeES<? super T, ?>> getListAttributes() {
        return listAttributes;
    }

    public Set<ListAttributeES<T, ?>> getDeclaredListAttributes() {
        return declaredListAttributes;
    }

    public Set<SetAttributeES<? super T, ?>> getSetAttributes() {
        return setAttributes;
    }

    public Set<SetAttributeES<T, ?>> getDeclaredSetAttributes() {
        return declaredSetAttributes;
    }

    public Set<MapAttributeES<? super T, ?, ?>> getMapAttributes() {
        return mapAttributes;
    }

    public Set<MapAttributeES<T, ?, ?>> getDeclaredMapAttributes() {
        return declaredMapAttributes;
    }
}