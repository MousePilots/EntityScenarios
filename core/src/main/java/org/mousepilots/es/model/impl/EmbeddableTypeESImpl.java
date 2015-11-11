package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.impl.classparameters.ManagedTypeParameters;
import org.mousepilots.es.model.EmbeddableTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements EmbeddableTypeES<T> {

    public EmbeddableTypeESImpl(ManagedTypeParameters<T> managedTypeParameters,
            TypeParameters<T> typeParameters) {
        super(managedTypeParameters, typeParameters);
    }
}