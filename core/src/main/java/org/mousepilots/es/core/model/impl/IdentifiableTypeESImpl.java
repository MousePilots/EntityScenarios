package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public abstract class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements IdentifiableTypeES<T>{

    private final int idAttributeOrdinal, idTypeOrdinal;
    private final Integer declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal;
    private final Set<Integer> idClassAttributes;
    private final boolean singleIdAttribute, versionAttribute;

    public IdentifiableTypeESImpl(
         int ordinal, 
         Class<T> javaType,
         Constructor<T> javaTypeConstructor,
         Class<? extends Proxy<T>> proxyType, 
         Constructor<? extends Proxy<T>> proxyTypeConstructor,
         Constructor<? extends HasValue<T>> hasValueConstructor,
         PersistenceType persistenceType, 
         Class<?> metamodelClass,
         Set<Integer> attributeOrdinals, 
         Integer superTypeOrdinal,
         Collection<Integer> subTypeOrdinals,
         int idAttributeOrdinal, 
         Integer declaredIdAttributeOrdinal, 
         Integer versionOrdinal, 
         Integer declaredVersionAttributeOrdinal,
         Set<Integer> idClassAttributeOrdinals,
         boolean singleIdAttributeOrdinal, 
         boolean versionAttributeOrdinal, 
         int idTypeOrdinal){
        super(ordinal, javaType, javaTypeConstructor, proxyType, proxyTypeConstructor, hasValueConstructor, persistenceType, metamodelClass, attributeOrdinals, superTypeOrdinal, subTypeOrdinals);
        this.idAttributeOrdinal = idAttributeOrdinal;
        this.declaredIdAttributeOrdinal = declaredIdAttributeOrdinal;
        this.versionAttributeOrdinal = versionOrdinal;
        this.declaredVersionAttributeOrdinal = declaredVersionAttributeOrdinal;
        this.idClassAttributes = idClassAttributeOrdinals;
        this.singleIdAttribute = singleIdAttributeOrdinal;
        this.versionAttribute = versionAttributeOrdinal;
        this.idTypeOrdinal = idTypeOrdinal;
    }

    /**
     * Get the attribute that forms the id of this identifiable type.
     * @return the attribute that forms the id.
     */
    public SingularAttributeES<? super T, ?> getId() {
        return (SingularAttributeES<T, ?>)AbstractMetamodelES.getInstance().getAttribute(idAttributeOrdinal);
    }

    /**
     * Get the attribute that is declared by this identifiable type and forms its id.
     * @return the attribute that is declared and forms the id.
     */
    public SingularAttributeES<T, ?> getDeclaredId() {
        return (SingularAttributeES<T, ?>)AbstractMetamodelES.getInstance().getAttribute(declaredIdAttributeOrdinal);
    }

    /**
     * Get the attribute that is the version of this identifiable type.
     * @return the attribute that is the version.
     */
    public SingularAttributeES<? super T, ?> getVersion() {
        return (SingularAttributeES<T, ?>)AbstractMetamodelES.getInstance().getAttribute(versionAttributeOrdinal);
    }

    /**
     * Get the attribute that is declared by this identifiable type and forms its version.
     * @return The attribute that is declared and forms the version.
     */
    public SingularAttributeES<T, ?> getDeclaredVersion() {
        return (SingularAttributeES<T, ?>)AbstractMetamodelES.getInstance().getAttribute(declaredVersionAttributeOrdinal);
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        Set<SingularAttribute<? super T, ?>> idAttributes = new TreeSet<>();
        for (int idClassOrdinal : idClassAttributes){
            idAttributes.add((SingularAttribute<T, ?>)AbstractMetamodelES.getInstance().getAttribute(idClassOrdinal));
        }
        return idAttributes;
    }

    @Override
    public TypeES<?> getIdType() {
        return AbstractMetamodelES.getInstance().getType(idAttributeOrdinal);
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
        return (IdentifiableTypeES<T>)super.getSuperType();
    }
}