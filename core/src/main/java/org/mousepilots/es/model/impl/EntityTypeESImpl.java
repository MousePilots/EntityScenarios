package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.impl.classparameters.IdentifiableTypeParameters;
import org.mousepilots.es.model.impl.classparameters.ManagedTypeParameters;
import org.mousepilots.es.model.EntityTypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The represented entity type.
 */
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements EntityTypeES<T>{

    private final BindableParameters<T> bindableParameters;

    public EntityTypeESImpl(BindableParameters<T> bindableParameters,
            IdentifiableTypeParameters<T> identifiableTypeParameters,
            ManagedTypeParameters<T> managedTypeParameters,
            TypeParameters<T> typeParameters) {
        super(identifiableTypeParameters, managedTypeParameters, typeParameters);
        this.bindableParameters = bindableParameters;
    }

    @Override
    public BindableType getBindableType() {
        return bindableParameters.getBindableType();
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableParameters.getBindableJavaType();
    }
}