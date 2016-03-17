package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented entity type
 */
public class MappedSuperclassTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements MappedSuperclassTypeES<T> {

    /**
     * Create a new instance of this class.
     * @param ordinal the ordinal of this mapped superclass type.
     * @param javaType the java type for this mapped superclass type.
     * @param metamodelClass the JPa meta model class for this mapped superclass type.
     * @param superTypeOrdinal the super type of this mapped superclass type.
     * @param subTypeOrdinals a set of sub types for this mapped superclass type.
     * @param hasValueConstructor the value of hasValueConstructor
     * @param javaTypeConstructor the zero-arg constructor for the
     * @param proxyTypeConstructor the zero-arg constructor for the
     * {@code proxyType} if existent, otherwise {@code null}
     * @param proxyType the {@link Proxy}-type for the {@code javaType}
     * @param attributeOrdinals the singular attributes that are part of this mapped superclass type
     * @param associationOrdinals
     * @param idClassAttributeOrdinals a set of attributes that form the id of this mapped superclass type.
     * @param idAttributeOrdinal
     * @param idTypeOrdinal
     * @param declaredVersionAttributeOrdinal the attribute that is declared by this mapped superclass type and forms its version.
     * @param versionAttributeOrdinal whether or not this mapped superclass type has a version attribute.
     * @param declaredIdAttributeOrdinal
     */
    public MappedSuperclassTypeESImpl(
         int ordinal,
         Class<T> javaType,
         Class<?> metamodelClass,
         int superTypeOrdinal,
         Collection<Integer> subTypeOrdinals,
         Constructor<? extends HasValue<T>> hasValueConstructor,
         Constructor<T> javaTypeConstructor,
         Constructor<? extends Proxy<T>> proxyTypeConstructor,
         Class<? extends Proxy<T>> proxyType,
         Set<Integer> attributeOrdinals,
         Collection<Integer> associationOrdinals,
         Set<Integer> idClassAttributeOrdinals,
         int idAttributeOrdinal, 
         int idTypeOrdinal,
         Integer declaredIdAttributeOrdinal,
         Integer versionAttributeOrdinal,
         Integer declaredVersionAttributeOrdinal){
         //TODO: check constructors
         super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor,
                proxyTypeConstructor, proxyType, attributeOrdinals, associationOrdinals, idClassAttributeOrdinals, idAttributeOrdinal,
                idTypeOrdinal, declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal);
}
    
    @Override
    public PersistenceType getPersistenceType(){
        return PersistenceType.MAPPED_SUPERCLASS;
    }

    //TODO: implement abstract methods from IdentifiableTypeESImpl
    @Override
    public <R, A> R accept(TypeVisitor<R, A> v, A arg) {
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