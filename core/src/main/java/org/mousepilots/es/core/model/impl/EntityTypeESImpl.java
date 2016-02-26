package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type.
 */
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T> implements EntityTypeES<T> {

    private final String name;
    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    /**
     *
     * @param ordinal
     * @param javaType
     * @param javaTypeConstructor
     * @param proxyType
     * @param proxyTypeConstructor
     * @param hasValueConstructor
     * @param persistenceType
     * @param metamodelClass
     * @param attributeOrdinals
     * @param superTypeOrdinal
     * @param subTypeOrdinals
     * @param idAttributeOrdinal
     * @param declaredIdAttributeOrdinal
     * @param versionOrdinal
     * @param declaredVersionAttributeOrdinal
     * @param idClassAttributeOrdinals
     * @param singleIdAttributeOrdinal
     * @param versionAttributeOrdinal
     * @param idTypeOrdinal
     * @param bindableType
     * @param bindableJavaType
     * @param singleIdAttribute
     * @param versionAttribute
     * @param idType
     * @param name
     * @param attributes
     */
    public EntityTypeESImpl(
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
            Set<Integer> subTypeOrdinals,
            int idAttributeOrdinal,
            Integer declaredIdAttributeOrdinal,
            Integer versionOrdinal,
            Integer declaredVersionAttributeOrdinal,
            Set<Integer> idClassAttributeOrdinals,
            boolean singleIdAttributeOrdinal,
            boolean versionAttributeOrdinal,
            int idTypeOrdinal,
            BindableType bindableType,
            Class<T> bindableJavaType,
            boolean singleIdAttribute,
            boolean versionAttribute,
            int idType,
            String name,
            Set<Integer> attributes) {
        super(ordinal, javaType, javaTypeConstructor, proxyType, proxyTypeConstructor, hasValueConstructor, persistenceType, metamodelClass, attributeOrdinals, superTypeOrdinal, subTypeOrdinals, 0, declaredIdAttributeOrdinal, versionOrdinal, declaredVersionAttributeOrdinal, idClassAttributeOrdinals, singleIdAttributeOrdinal, versionAttributeOrdinal, idTypeOrdinal);
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BindableType getBindableType() {
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableJavaType;
    }

}
