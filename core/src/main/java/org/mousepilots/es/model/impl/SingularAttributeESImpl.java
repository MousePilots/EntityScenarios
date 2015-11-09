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
public class SingularAttributeESImpl<X, T> extends AttributeESImpl<X, T> implements SingularAttributeES<X, T> {

    private final boolean generated;
    private final Generator generator;
    private final Class<T> bindableJavaType;
    private final boolean id;
    private final boolean version;
    private final boolean optional;
    private final Type<T> type;
    private final BindableType bindableType;

    public SingularAttributeESImpl(boolean generated, Generator generator, Class<T> bindableJavaType, boolean id, boolean version, boolean optional, Type<T> type, BindableType bindableType, String name, PersistentAttributeType persistentAttributeType, MemberES javaMember, int ordinal, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType, Class<T> javaType) {
        super(name, persistentAttributeType, javaMember, ordinal, readOnly, collection, association, declaringType, javaType);
        this.generated = generated;
        this.generator = generator;
        this.bindableJavaType = bindableJavaType;
        this.id = id;
        this.version = version;
        this.optional = optional;
        this.type = type;
        this.bindableType = bindableType;
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
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        return bindableJavaType;
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