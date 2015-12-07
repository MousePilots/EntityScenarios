package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements IdentifiableTypeES<T>{

    private final SingularAttributeES<? super T, ?> id;
    private final SingularAttributeES<T, ?> declaredId;
    private final SingularAttributeES<? super T, ?> version;
    private final SingularAttributeES<T, ?> declaredVersion;
    private final Set<SingularAttribute<? super T, ?>> idClassAttributes;
    private final boolean singleIdAttribute, versionAttribute;
    private final TypeES<?> idType;

    /**
     * Create a new instance of this class.
     * @param id the attribute that forms the id of this identifiable type.
     * @param declaredId the attribute that is declared by this identifiable type and forms its id.
     * @param version the attribute that is the version of this identifiable type.
     * @param declaredVersion the attribute that is declared by this identifiable type and forms its version.
     * @param superType the super type of this identifiable type.
     * @param idClassAttributes a set of attributes that form the id of this identifiable type.
     * @param singleIdAttribute whether or not this identifiable type has a simple primary key or a composite primary key.
     * @param versionAttribute whether or not this identifiable type has a version attribute.
     * @param idType the {@link TypeES} that represents the id for this identifiable type.
     * @param name the name of this identifiable type.
     * @param ordinal the ordinal of this identifiable type.
     * @param javaType the java type for this identifiable type.
     * @param persistenceType the {@link PersistenceType} for this identifiable type.
     * @param javaClassName the name of the java class that represents this identifiable type.
     * @param instantiable whether or not this identifiable type is instanciable.
     * @param attributes the attributes this identifiable contains.
     * @param metamodelClass the JPa meta model class for this identifiable type.
     * @param subTypes a set of sub types for this identifiable type.
     */
    public IdentifiableTypeESImpl(SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            Set<SingularAttribute<? super T, ?>> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType,
            String name, int ordinal, Class<T> javaType,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            Set<Attribute<? super T, ?>> attributes, TypeES<? super T> superType,
            Collection<TypeES<? extends T>> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName,
                instantiable, metamodelClass, attributes, superType, subTypes);
        this.id = id;
        this.declaredId = declaredId;
        this.version = version;
        this.declaredVersion = declaredVersion;
        this.idClassAttributes = idClassAttributes;
        this.singleIdAttribute = singleIdAttribute;
        this.versionAttribute = versionAttribute;
        this.idType = idType;
    }

    /**
     * Get the attribute that forms the id of this identifiable type.
     * @return the attribute that forms the id.
     */
    public SingularAttributeES<? super T, ?> getId() {
        return id;
    }

    /**
     * Get the attribute that is declared by this identifiable type and forms its id.
     * @return the attribute that is declared and forms the id.
     */
    public SingularAttributeES<T, ?> getDeclaredId() {
        return declaredId;
    }

    /**
     * Get the attribute that is the version of this identifiable type.
     * @return the attribute that is the version.
     */
    public SingularAttributeES<? super T, ?> getVersion() {
        return version;
    }

    /**
     * Get the attribute that is declared by this identifiable type and forms its version.
     * @return The attribute that is declared and forms the version.
     */
    public SingularAttributeES<T, ?> getDeclaredVersion() {
        return declaredVersion;
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        return idClassAttributes;
    }

    @Override
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
    public boolean hasSingleIdAttribute() {
        return singleIdAttribute;
    }

    @Override
    public boolean hasVersionAttribute() {
        return versionAttribute;
    }

    @Override
    public String toString() {
        return super.toString() + ", Identifiable id: " + getId().getName()
                + ", version: " + getVersion().getName();
    }

    @Override
    public IdentifiableTypeES<? super T> getSupertype() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}