package org.mousepilots.es.model.impl;

import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.CollectionAttributeES;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.MappedSuperclassTypeES;
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 * @param <T> The represented entity type
 */
public class MappedSuperclassTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements MappedSuperclassTypeES<T> {

    public MappedSuperclassTypeESImpl(SingularAttributeES<? super T, ?> id, SingularAttributeES<T, ?> declaredId, SingularAttributeES<? super T, ?> version, SingularAttributeES<T, ?> declaredVersion, IdentifiableTypeES<? super T> superType, Set<SingularAttribute<? super T, ?>> idClassAttributes, boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType, Set<Attribute<? super T, ?>> attributes, Set<Attribute<T, ?>> declaredAttributes, Set<SingularAttribute<? super T, ?>> singularAttributes, Set<SingularAttribute<T, ?>> declaredSingularAttributes, Set<CollectionAttributeES<? super T, ?>> collectionAttributes, Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes, Set<ListAttributeES<? super T, ?>> listAttributes, Set<ListAttributeES<T, ?>> declaredListAttributes, Set<SetAttributeES<? super T, ?>> setAttributes, Set<SetAttributeES<T, ?>> declaredSetAttributes, Set<MapAttributeES<? super T, ?, ?>> mapAttributes, Set<MapAttributeES<T, ?, ?>> declaredMapAttributes, String name, int ordinal, Class<T> javaType, PersistenceType persistenceType, String javaClassName, boolean instantiable, Class<? extends Type<T>> metamodelClass, SortedSet<TypeES<? super T>> superTypes, SortedSet<TypeES<? extends T>> subTypes) {
        super(id, declaredId, version, declaredVersion, superType, idClassAttributes, singleIdAttribute, versionAttribute, idType, attributes, declaredAttributes, singularAttributes, declaredSingularAttributes, collectionAttributes, declaredCollectionAttributes, listAttributes, declaredListAttributes, setAttributes, declaredSetAttributes, mapAttributes, declaredMapAttributes, name, ordinal, javaType, persistenceType, javaClassName, instantiable, metamodelClass, superTypes, subTypes);
    }
}