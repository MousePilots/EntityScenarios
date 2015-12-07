package org.mousepilots.es.core.model;

import javax.persistence.metamodel.Metamodel;

/**
 * Provides access to the metamodel of persistent
 * entities in the persistence unit.
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 * @see Metamodel
 */
public interface MetaModelES extends Metamodel{

    @Override
    <X> EntityTypeES<X> entity(Class<X> cls);

    @Override
    <X> ManagedTypeES<X> managedType(Class<X> cls);

    /**
     * Gets a {@link ManagedTypeES} of the expected return tye {@code M}
     * @param <X>
     * @param <M> a 
     * @param managedTypeClass
     * @param javaType
     * @return 
     */
    <X,M extends ManagedTypeES<X>> M managedType(Class<X> javaType, Class<M> managedTypeClass);
    

    @Override
    <X> EmbeddableTypeES<X> embeddable(Class<X> cls);
}