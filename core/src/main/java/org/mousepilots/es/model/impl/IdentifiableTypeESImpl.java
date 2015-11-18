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
import org.mousepilots.es.model.SetAttributeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements IdentifiableTypeES<T>{

    private final SingularAttributeES<? super T, ?> id;
    private final SingularAttributeES<T, ?> declaredId;
    private final SingularAttributeES<? super T, ?> version;
    private final SingularAttributeES<T, ?> declaredVersion;
    private final IdentifiableTypeES<? super T> superType;
    private final Set<SingularAttribute<? super T, ?>> idClassAttributes;
    private final boolean singleIdAttribute, versionAttribute;
    private final TypeES<?> idType;

    public IdentifiableTypeESImpl(SingularAttributeES<? super T, ?> id, SingularAttributeES<T, ?> declaredId, SingularAttributeES<? super T, ?> version, SingularAttributeES<T, ?> declaredVersion, IdentifiableTypeES<? super T> superType, Set<SingularAttribute<? super T, ?>> idClassAttributes, boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType, Set<Attribute<? super T, ?>> attributes, Set<Attribute<T, ?>> declaredAttributes, Set<SingularAttribute<? super T, ?>> singularAttributes, Set<SingularAttribute<T, ?>> declaredSingularAttributes, Set<CollectionAttributeES<? super T, ?>> collectionAttributes, Set<CollectionAttributeES<T, ?>> declaredCollectionAttributes, Set<ListAttributeES<? super T, ?>> listAttributes, Set<ListAttributeES<T, ?>> declaredListAttributes, Set<SetAttributeES<? super T, ?>> setAttributes, Set<SetAttributeES<T, ?>> declaredSetAttributes, Set<MapAttributeES<? super T, ?, ?>> mapAttributes, Set<MapAttributeES<T, ?, ?>> declaredMapAttributes, String name, int ordinal, Class<T> javaType, PersistenceType persistenceType, String javaClassName, boolean instantiable, Class<? extends Type<T>> metamodelClass, SortedSet<TypeES<? super T>> superTypes, SortedSet<TypeES<? extends T>> subTypes) {
        super(attributes, declaredAttributes, singularAttributes, declaredSingularAttributes, collectionAttributes, declaredCollectionAttributes, listAttributes, declaredListAttributes, setAttributes, declaredSetAttributes, mapAttributes, declaredMapAttributes, name, ordinal, javaType, persistenceType, javaClassName, instantiable, metamodelClass, superTypes, subTypes);
        this.id = id;
        this.declaredId = declaredId;
        this.version = version;
        this.declaredVersion = declaredVersion;
        this.superType = superType;
        this.idClassAttributes = idClassAttributes;
        this.singleIdAttribute = singleIdAttribute;
        this.versionAttribute = versionAttribute;
        this.idType = idType;
    }

    public SingularAttributeES<? super T, ?> getId() {
        return id;
    }

    public SingularAttributeES<T, ?> getDeclaredId() {
        return declaredId;
    }

    public SingularAttributeES<? super T, ?> getVersion() {
        return version;
    }

    public SingularAttributeES<T, ?> getDeclaredVersion() {
        return declaredVersion;
    }

    public IdentifiableTypeES<? super T> getSuperType() {
        return superType;
    }

    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        return idClassAttributes;
    }

    public boolean isSingleIdAttribute() {
        return singleIdAttribute;
    }

    public boolean isVersionAttribute() {
        return versionAttribute;
    }

    public TypeES<?> getIdType() {
        return idType;
    }



    @Override
    public <Y> SingularAttributeES<? super T, Y> getId(Class<Y> type) {
        return (SingularAttributeES<? super T, Y>) getId();
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredId(Class<Y> type) {
        return (SingularAttributeES<T, Y>) getDeclaredId();
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getVersion(Class<Y> type) {
        return (SingularAttributeES<? super T, Y>) getVersion();
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredVersion(Class<Y> type) {
        return (SingularAttributeES<T, Y>) getDeclaredVersion();
    }

    @Override
    public IdentifiableTypeES<? super T> getSupertype() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSingleIdAttribute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasVersionAttribute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}