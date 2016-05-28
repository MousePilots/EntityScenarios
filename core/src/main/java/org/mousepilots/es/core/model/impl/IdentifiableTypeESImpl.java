package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public abstract class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements IdentifiableTypeES<T>{

    private final int idAttributeOrdinal, idTypeOrdinal;
    private final Integer declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal;
    private final Set<Integer> idClassAttributes = new TreeSet<>();

    /**
     * 
     * @param ordinal
     * @param javaType
     * @param metamodelClass
     * @param superTypeOrdinal
     * @param subTypeOrdinals
     * @param hasValueConstructor
     * @param javaTypeConstructor
     * @param getOwners
     * @param proxyTypeConstructor
     * @param proxyType
     * @param attributeOrdinals
     * @param declaredAttributes
     * @param associationOrdinals
     * @param idClassAttributeOrdinals
     * @param idAttributeOrdinal
     * @param idTypeOrdinal
     * @param declaredIdAttributeOrdinal
     * @param versionAttributeOrdinal
     * @param declaredVersionAttributeOrdinal 
     */
    public IdentifiableTypeESImpl(
         int ordinal,
         Class<T> javaType,
         Class<?> metamodelClass,
         Integer superTypeOrdinal,
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<? super T>> hasValueConstructor,
         Constructor<T> javaTypeConstructor,
         Getter<? super T, Set<String>> getOwners,
         Constructor<? extends Proxy<T>> proxyTypeConstructor,
         Class<? extends Proxy<T>> proxyType,
         Collection<Integer> attributeOrdinals,
         Collection<AttributeES<T, ?>> declaredAttributes,
         Collection<Integer> associationOrdinals,
         Collection<Integer> idClassAttributeOrdinals,
         int idAttributeOrdinal, 
         int idTypeOrdinal,
         Integer declaredIdAttributeOrdinal,
         Integer versionAttributeOrdinal,
         Integer declaredVersionAttributeOrdinal){
        super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor, getOwners, proxyTypeConstructor, proxyType, attributeOrdinals, declaredAttributes, associationOrdinals);
        this.idAttributeOrdinal = idAttributeOrdinal;
        this.declaredIdAttributeOrdinal = declaredIdAttributeOrdinal;
        this.versionAttributeOrdinal = versionAttributeOrdinal;
        this.declaredVersionAttributeOrdinal = declaredVersionAttributeOrdinal;
        this.idClassAttributes.addAll(idClassAttributeOrdinals);
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
        return AbstractMetamodelES.getInstance().getType(idTypeOrdinal);
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
    public String toString() {
        return super.toString() + ", Identifiable id: " + getId().getName()
                + ", version: " + getVersion().getName();
    }

    @Override
    public IdentifiableTypeES<? super T> getSupertype() {
        return (IdentifiableTypeES<T>)super.getSuperType();
    }

    @Override
    public final boolean hasSingleIdAttribute() {
        return true;
    }

    @Override
    public final boolean hasVersionAttribute() {
        return versionAttributeOrdinal!=null;
    }

    @Override
    public final int hash(Proxy<T> proxy) {
        final Object id = IdentifiableTypeUtils.getId(this, proxy.__subject());
        return Objects.hash(proxy.getClass(),id);
    }
    
}
