package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.IdentifiableTypeES;

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
    public <Y> SingularAttribute<? super T, Y> getId(Class<Y> type) {
        return (SingularAttribute<? super T, Y>) identifiableTypeParameters
                .getId();
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredId(Class<Y> type) {
        return (SingularAttribute<T, Y>) identifiableTypeParameters
                .getDeclaredId();
    }

    @Override
    public <Y> SingularAttribute<? super T, Y> getVersion(Class<Y> type) {
        return (SingularAttribute<? super T, Y>) identifiableTypeParameters
                .getVersion();
    }

    @Override
    public <Y> SingularAttribute<T, Y> getDeclaredVersion(Class<Y> type) {
        return (SingularAttribute<T, Y>) identifiableTypeParameters
                .getDeclaredVersion();
    }

    @Override
    public IdentifiableType<? super T> getSupertype() {
        return identifiableTypeParameters.getSuperType();
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
}