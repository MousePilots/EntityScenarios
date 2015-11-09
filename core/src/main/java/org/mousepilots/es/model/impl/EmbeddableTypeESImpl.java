package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T> implements EmbeddableTypeES<T> {

    public EmbeddableTypeESImpl(String javaClassName, String name, int ordinal, boolean instantiable, PersistenceType persistenceType, Class<T> javaType, Class<? extends Type<T>> metamodelClass, Collection<TypeES<? super T>> superTypes, Collection<TypeES<? extends T>> subTypes) {
        super(javaClassName, name, ordinal, instantiable, persistenceType, javaType, metamodelClass, superTypes, subTypes);
    }
}