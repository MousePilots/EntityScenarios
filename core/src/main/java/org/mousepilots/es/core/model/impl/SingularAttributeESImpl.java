package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import javax.persistence.Id;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.Generator;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <X> The type containing the represented wrapAttribute
 * @param <Y> The type of the represented wrapAttribute
 */
public class SingularAttributeESImpl<X, Y> extends AttributeESImpl<X, Y> implements SingularAttributeES<X, Y> {

    private final boolean generated;
    private final Generator generator;
    private final boolean id;
    private final boolean version;
    private final boolean optional;
    private final TypeES<Y> type;
    private final BindableType bindableType;
    private final Class<Y> bindableJavaType;

    public SingularAttributeESImpl(
            String name, 
            int ordinal, 
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            Class<Y> javaType, 
            PersistentAttributeType persistentAttributeType, 
            MemberES javaMember, 
            boolean readOnly, 
            AssociationES association, 
            ManagedTypeES<X> declaringType, 
            Constructor<HasValue> hasValueChangeConstructor,         
            boolean generated,
            Generator generator,
            boolean id,
            boolean version,
            boolean optional,
            TypeES<Y> type,
            BindableType bindableType,
            Class<Y> bindableJavaType) {
        super(name, ordinal, superOrdinal, subOrdinals, -1, persistentAttributeType, javaMember, readOnly, association, declaringType, hasValueChangeConstructor);
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
    public Class<Y> getBindableJavaType() {
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
    public TypeES<Y> getType() {
        return type;
    }

    /**
     * Sets the {@code value} onto the {@code hasValue}. For basic or embeddable values, the actual value is set.
     * For identifiable, the {@link Id}-value is set.
     * @param <T> the type of {@code value}.
     * @param hasValue an empty {@link HasValue}
     * @param valueType the {@link TypeES} of the {@code value} to be wrapped
     * @param value the value to be wrapped
     */
    protected static <T> void setSingularValueForChange(HasValue hasValue, TypeES valueType, T value){
        final Type.PersistenceType persistenceType = valueType.getPersistenceType();
        switch(persistenceType){
            case BASIC :
            case EMBEDDABLE : {
                //Type is basic or embeddable so just wrap the value.
                hasValue.setValue(value);
                break;
            }
            case ENTITY : {
                //Type is a entity or mapped superclass which is identifiable, so casting should be safe.
                //Wrap the id of the identifiable.
                IdentifiableTypeESImpl identifiableType = (IdentifiableTypeESImpl)valueType;
                final Object id = value == null ? null : identifiableType.getId().getJavaMember().get(value);
                hasValue.setValue(id);
                break;
            }
            default : throw new UnsupportedOperationException(
                    "unkown PersistenceType " + persistenceType + " for " + valueType
            );
        }
    }

    @Override
    public HasValue wrap(Y value) {
        final HasValue hv = this.getHasValueChangeConstructor().invoke();
        setSingularValueForChange(hv, getType(), value);
        return hv;
    }

    @Override
    public boolean isCollection() {
        return false;
    }
    
}