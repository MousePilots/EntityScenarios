package org.mousepilots.es.model;

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

    @Override
    <X> EmbeddableTypeES<X> embeddable(Class<X> cls);
}