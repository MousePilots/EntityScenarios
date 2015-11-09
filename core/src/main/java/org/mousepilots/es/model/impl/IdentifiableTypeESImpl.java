package org.mousepilots.es.model.impl;

import java.util.Collection;
import java.util.Set;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T> implements IdentifiableTypeES<T>{

    private final SingularAttributeES<? super T, ?> id;
    private final SingularAttributeES<T, ?> declaredId;
    private final SingularAttributeES<? super T, ?> version;
    private final SingularAttributeES<T, ?> declaredVersion;
    private final IdentifiableTypeES<? super T> superType;
    private final boolean singleIdAttribute, versionAttribute;
    private final TypeES<?> idType;

    public IdentifiableTypeESImpl(SingularAttributeES<? super T, ?> id, SingularAttributeES<T, ?> declaredId, SingularAttributeES<? super T, ?> version, SingularAttributeES<T, ?> declaredVersion, IdentifiableTypeES<? super T> superType, boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType, String javaClassName, String name, int ordinal, boolean instantiable, PersistenceType persistenceType, Class<T> javaType, Class<? extends Type<T>> metamodelClass, Collection<TypeES<? super T>> superTypes, Collection<TypeES<? extends T>> subTypes) {
        super(javaClassName, name, ordinal, instantiable, persistenceType, javaType, metamodelClass, superTypes, subTypes);
        this.id = id;
        this.declaredId = declaredId;
        this.version = version;
        this.declaredVersion = declaredVersion;
        this.superType = superType;
        this.singleIdAttribute = singleIdAttribute;
        this.versionAttribute = versionAttribute;
        this.idType = idType;
    }

    @Override
    public <Y> SingularAttribute<? super T, Y> getId(Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredId(Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <Y> SingularAttribute<? super T, Y> getVersion(Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredVersion(Class<Y> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IdentifiableType<? super T> getSupertype() {
        return superType;
    }

    @Override
    public boolean hasSingleIdAttribute() {
        return singleIdAttribute;
    }

    @Override
    public boolean hasVersionAttribute() {
        return versionAttribute;
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type<?> getIdType() {
        return idType;
    }
}