package org.mousepilots.es.model.impl;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <X> The type containing the represented attribute
 * @param <T> The type of the represented attribute
 */
public class SingularAttributeESImpl<X, T> extends AttributeESImpl<X, T>
    implements SingularAttributeES<X, T> {

    private final boolean generated;
    private final Generator generator;
    private final boolean id;
    private final boolean version;
    private final boolean optional;
    private final Type<T> type;
    private final BindableParameters<T> bindableParameters;

    public SingularAttributeESImpl(boolean generated, Generator generator,
            boolean id, boolean version, boolean optional, Type<T> type,
            BindableParameters<T> bindableParameters,
            AttributeTypeParameters<T> attributeTypeParameters,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES declaringType) {
        super(attributeTypeParameters, persistentAttributeType, javaMember,
                readOnly, collection, association, declaringType);
        this.generated = generated;
        this.generator = generator;
        this.id = id;
        this.version = version;
        this.optional = optional;
        this.type = type;
        this.bindableParameters = bindableParameters;
    }

    @Override
    public boolean isGenerated() {
        return generated;
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public BindableType getBindableType() {
        return bindableParameters.getBindableType();
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableParameters.getBindableJavaType();
    }

    @Override
    public boolean isId() {
        return id;
    }

    @Override
    public boolean isVersion() {
        return version;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public Type<T> getType() {
        return type;
    }
}