package org.mousepilots.es.core.model;

import javax.persistence.metamodel.MappedSuperclassType;

/**
 * Instances of the type MappedSuperclassType represent mapped superclass types.
 *
 * @param <T> The represented entity type
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 * @see IdentifiableTypeES
 * @see MappedSuperclassType
 */
public interface MappedSuperclassTypeES<T> extends IdentifiableTypeES<T>, MappedSuperclassType<T> {

    @Override
    public default void accept(TypeVisitor v) {
        v.visit(this);
    }
}
