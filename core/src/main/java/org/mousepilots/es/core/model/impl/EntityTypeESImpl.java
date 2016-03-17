package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeVisitor;
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
      * @param associationOrdinals
     * @param idClassAttributeOrdinals
     * @param idAttributeOrdinal
     * @param idTypeOrdinal
     * @param declaredIdAttributeOrdinal
     * @param versionAttributeOrdinal
     * @param declaredVersionAttributeOrdinal
     * @param bindableType
     * @param bindableJavaType
     * @param name
      */
    public EntityTypeESImpl(
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
         Integer declaredVersionAttributeOrdinal,
         BindableType bindableType,
         Class<T> bindableJavaType,
         String name){
        super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor, proxyTypeConstructor, proxyType, attributeOrdinals, associationOrdinals, idClassAttributeOrdinals, idAttributeOrdinal, idTypeOrdinal, declaredIdAttributeOrdinal, versionAttributeOrdinal, declaredVersionAttributeOrdinal);
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
    
    @Override
    public PersistenceType getPersistenceType(){
        return PersistenceType.ENTITY;
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
