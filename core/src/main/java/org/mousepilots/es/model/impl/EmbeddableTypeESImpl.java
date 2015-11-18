package org.mousepilots.es.model.impl;

import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements EmbeddableTypeES<T> {

    public EmbeddableTypeESImpl(Set<Attribute<? super T, ?>> attributes, Set<Attribute<T, ?>> declaredAttributes, Set<SingularAttribute<? super T, ?>> singularAttributes, Set<SingularAttribute<T, ?>> declaredSingularAttributes, Set<CollectionAttributeES<? super T, ?>> collectionAttributes, Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes, Set<ListAttributeES<? super T, ?>> listAttributes, Set<ListAttributeES<T, ?>> declaredListAttributes, Set<SetAttributeES<? super T, ?>> setAttributes, Set<SetAttributeES<T, ?>> declaredSetAttributes, Set<MapAttributeES<? super T, ?, ?>> mapAttributes, Set<MapAttributeES<T, ?, ?>> declaredMapAttributes, String name, int ordinal, Class<T> javaType, PersistenceType persistenceType, String javaClassName, boolean instantiable, Class<? extends Type<T>> metamodelClass, SortedSet<TypeES<? super T>> superTypes, SortedSet<TypeES<? extends T>> subTypes) {
        super(attributes, declaredAttributes, singularAttributes, declaredSingularAttributes, collectionAttributes, declaredCollectionAttributes, listAttributes, declaredListAttributes, setAttributes, declaredSetAttributes, mapAttributes, declaredMapAttributes, name, ordinal, javaType, persistenceType, javaClassName, instantiable, metamodelClass, superTypes, subTypes);
    }
}