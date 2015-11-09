package org.mousepilots.es.model.impl;

import java.util.Collection;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.EntityTypeES;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The represented entity type.
 */
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T> implements EntityTypeES<T>{

    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    public EntityTypeESImpl(BindableType bindableType, Class<T> bindableJavaType, SingularAttributeES<? super T, ?> id, SingularAttributeES<T, ?> declaredId, SingularAttributeES<? super T, ?> version, SingularAttributeES<T, ?> declaredVersion, IdentifiableTypeES<? super T> superType, boolean singleIdAttribute, boolean versionAttribute, TypeES<?> idType, String javaClassName, String name, int ordinal, boolean instantiable, PersistenceType persistenceType, Class<T> javaType, Class<? extends Type<T>> metamodelClass, Collection<TypeES<? super T>> superTypes, Collection<TypeES<? extends T>> subTypes) {
        super(id, declaredId, version, declaredVersion, superType, singleIdAttribute, versionAttribute, idType, javaClassName, name, ordinal, instantiable, persistenceType, javaType, metamodelClass, superTypes, subTypes);
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
    }

    @Override
    public BindableType getBindableType() {
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableJavaType;
    }
}