package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.impl.classparameters.AttributeParameters;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
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
    private final TypeES<T> type;
    private final BindableParameters<T> bindableParameters;

    public SingularAttributeESImpl(boolean generated, Generator generator,
            boolean id, boolean version, boolean optional, TypeES<T> type,
            BindableParameters<T> bindableParameters,
            AttributeParameters<T> attributeParameters) {
        super(attributeParameters);
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
    public TypeES<T> getType() {
        return type;
    }
}