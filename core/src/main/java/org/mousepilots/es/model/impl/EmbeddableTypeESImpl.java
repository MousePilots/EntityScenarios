package org.mousepilots.es.model.impl;

import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements EmbeddableTypeES<T> {

    public EmbeddableTypeESImpl(
            AttributeTypeParameters<T> attributeTypeParameters,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(attributeTypeParameters, persistenceType, javaClassName,
                instantiable, metamodelClass, superTypes, subTypes);
    }    
}