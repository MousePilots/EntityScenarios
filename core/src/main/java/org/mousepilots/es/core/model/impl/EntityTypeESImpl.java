package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type.
 */
public final class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T> implements EntityTypeES<T> {

    private final String name;


    /**
      * @param ordinal the ordinal of this entity type.
      * @param javaType the java type for this entity type.
      * @param javaTypeConstructor the zero-arg constructor for the
      * {@code javaType} if existent, otherwise {@code null}
      * @param proxyType the {@link Proxy}-type for the {@code javaType}
      * @param proxyTypeConstructor the zero-arg constructor for the
      * {@code proxyType} if existent, otherwise {@code null}
      * @param hasValueConstructor the value of hasValueConstructor
      * @param metamodelClass the JPa meta model class for this entity type.
      * @param attributeOrdinals the singular attributes that are part of this
      * embeddable type.
      * @param superTypeOrdinal the supertype of this entity type.
      * @param subTypeOrdinals a set of sub types for this entity type.
     * @param declaredAttributes
      * @param associationOrdinals
     * @param idClassAttributeOrdinals
     * @param idAttributeOrdinal
     * @param idTypeOrdinal
     * @param declaredIdAttributeOrdinal
     * @param versionAttributeOrdinal
     * @param declaredVersionAttributeOrdinal
     * @param name
      */
    public EntityTypeESImpl(
         int ordinal,
         Class<T> javaType,
         Class<?> metamodelClass,
         int superTypeOrdinal,
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<? super T>> hasValueConstructor,
         Constructor<T> javaTypeConstructor,
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
         Integer declaredVersionAttributeOrdinal,
         String name){
        super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor, proxyTypeConstructor, proxyType, attributeOrdinals, declaredAttributes, associationOrdinals, idClassAttributeOrdinals, idAttributeOrdinal, idTypeOrdinal, declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }





}
