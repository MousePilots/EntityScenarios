package org.mousepilots.es.model.impl;

import java.util.SortedSet;
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
public class EntityTypeESImpl<T> extends IdentifiableTypeESImpl<T>
    implements EntityTypeES<T>{

    private final BindableParameters<T> bindableParameters;

    public EntityTypeESImpl(BindableParameters<T> bindableParameters,
            SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            IdentifiableTypeES<? super T> superType, boolean singleIdAttribute,
            boolean versionAttribute, TypeES<?> idType,
            AttributeTypeParameters<T> attributeTypeParameters,
            PersistenceType persistenceType, String javaClassName,
            boolean instantiable, Class<? extends Type<T>> metamodelClass,
            SortedSet<TypeES<? super T>> superTypes,
            SortedSet<TypeES<? extends T>> subTypes) {
        super(id, declaredId, version, declaredVersion, superType,
                singleIdAttribute, versionAttribute, idType,
                attributeTypeParameters, persistenceType, javaClassName,
                instantiable, metamodelClass, superTypes, subTypes);
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