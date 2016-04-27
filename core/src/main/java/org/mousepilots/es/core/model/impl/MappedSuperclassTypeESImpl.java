package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type
 */
public class MappedSuperclassTypeESImpl<T> extends IdentifiableTypeESImpl<T> implements MappedSuperclassTypeES<T> {

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
    public MappedSuperclassTypeESImpl(
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
         super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor,getOwners,
                proxyTypeConstructor, proxyType, attributeOrdinals, declaredAttributes, associationOrdinals, idClassAttributeOrdinals, idAttributeOrdinal,
                idTypeOrdinal, declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal);
    }

    @Override
    public final <R, A> R accept(TypeVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }
    
}