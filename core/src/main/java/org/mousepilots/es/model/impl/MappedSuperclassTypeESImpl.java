package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.impl.classparameters.IdentifiableTypeParameters;
import org.mousepilots.es.model.impl.classparameters.ManagedTypeParameters;
import org.mousepilots.es.model.MappedSuperclassTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The represented entity type
 */
public class MappedSuperclassTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements MappedSuperclassTypeES<T> {

    public MappedSuperclassTypeESImpl(
            IdentifiableTypeParameters<T> identifiableTypeParameters,
            ManagedTypeParameters<T> managedTypeParameters,
            TypeParameters<T> typeParameters) {
        super(identifiableTypeParameters, managedTypeParameters,
                typeParameters);
    }
}