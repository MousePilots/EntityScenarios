package org.mousepilots.es.model.impl;

import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.BasicTypeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type for this BasicType.
 */
public class BasicTypeESImpl<T> extends TypeESImpl<T>
    implements BasicTypeES<T> {

    public BasicTypeESImpl(AttributeTypeParameters<T> attributeTypeParameters,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(attributeTypeParameters, persistenceType, javaClassName,
                instantiable, metamodelClass, superTypes, subTypes);
    }    
}