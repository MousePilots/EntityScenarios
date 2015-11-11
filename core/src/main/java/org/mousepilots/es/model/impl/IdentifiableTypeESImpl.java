package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.TypeParameters;
import org.mousepilots.es.model.impl.classparameters.IdentifiableTypeParameters;
import org.mousepilots.es.model.impl.classparameters.ManagedTypeParameters;
import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 * @param <T> The represented entity or mapped superclass type.
 */
public class IdentifiableTypeESImpl<T> extends ManagedTypeESImpl<T>
    implements IdentifiableTypeES<T>{

    private final IdentifiableTypeParameters<T> identifiableTypeParameters;

    public IdentifiableTypeESImpl(
            IdentifiableTypeParameters<T> identifiableTypeParameters,
            ManagedTypeParameters<T> managedTypeParameters,
            TypeParameters<T> typeParameters) {
        super(managedTypeParameters, typeParameters);
        this.identifiableTypeParameters = identifiableTypeParameters;
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getId(Class<Y> type) {
        return (SingularAttributeES<? super T, Y>) identifiableTypeParameters
                .getId();
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredId(Class<Y> type) {
        return (SingularAttributeES<T, Y>) identifiableTypeParameters
                .getDeclaredId();
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getVersion(Class<Y> type) {
        return (SingularAttributeES<? super T, Y>) identifiableTypeParameters
                .getVersion();
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredVersion(Class<Y> type) {
        return (SingularAttributeES<T, Y>) identifiableTypeParameters
                .getDeclaredVersion();
    }

    @Override
    public boolean hasSingleIdAttribute() {
        return identifiableTypeParameters.hasSingleIdAttribute();
    }

    @Override
    public boolean hasVersionAttribute() {
        return identifiableTypeParameters.hasVersionAttribute();
    }

    @Override
    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        return identifiableTypeParameters.getIdClassAttributes();
    }

    @Override
    public Type<?> getIdType() {
        return identifiableTypeParameters.getIdType();
    }

    @Override
    public IdentifiableTypeES<? super T> getSupertype() {
        return identifiableTypeParameters.getSuperType();
    }
}