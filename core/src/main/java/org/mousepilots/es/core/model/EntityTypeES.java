package org.mousepilots.es.core.model;

import javax.persistence.metamodel.EntityType;

/**
 * Instances of the type {@link EntityTypeES} represent entity types.
 * @param <T> The represented entity type.
 * @see IdentifiableTypeES
 * @see EntityType
 * @see BindableES
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface EntityTypeES<T> extends IdentifiableTypeES<T>, EntityType<T>, BindableES<T> {

    @Override
    default BindableType getBindableType() {
        return BindableType.ENTITY_TYPE;
    }

    @Override
    default Class<T> getBindableJavaType() {
        return getJavaType();
    }
    
    @Override
    default PersistenceType getPersistenceType(){
        return PersistenceType.ENTITY;
    }    
}