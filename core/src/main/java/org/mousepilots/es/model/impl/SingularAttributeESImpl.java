package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
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
    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    public SingularAttributeESImpl(boolean generated, Generator generator, boolean id, boolean version, boolean optional, TypeES<T> type, BindableType bindableType, Class<T> bindableJavaType, String name, int ordinal, Class<T> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<X> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
        this.generated = generated;
        this.generator = generator;
        this.id = id;
        this.version = version;
        this.optional = optional;
        this.type = type;
        this.bindableType = bindableType;
        this.bindableJavaType = bindableJavaType;
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
    public TypeES<T> getType() {
        return type;
    }

    @Override
    public HasValue wrapForChange(T value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HasValue wrapForDTO(T value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}