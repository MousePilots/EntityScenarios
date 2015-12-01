package org.mousepilots.es.model.impl;

import java.lang.reflect.Member;
import javax.persistence.Id;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 27-11-2015
 * @param <X> The type containing the represented attribute
 * @param <T> The type of the represented attribute
 */
public class SingularAttributeESImpl<X, T> extends AttributeESImpl<X, T, T>
    implements SingularAttributeES<X, T> {

    private final boolean generated;
    private final Generator generator;
    private final boolean id;
    private final boolean version;
    private final boolean optional;
    private final TypeES<T> type;
    private final BindableType bindableType;
    private final Class<T> bindableJavaType;

    /**
     * Create a new instance of this class.
     * @param generated whether or not the value of this singular attribute is generated.
     * @param generator if {@code generated} is {@code true} get the {@link Generator} that generated this singular attribute.
     * @param id whether or not this attribute is part of the id of an identifiable type.
     * @param version whether or not this attribute represents the version of an identifiable type.
     * @param optional whether or not this singular attribute is optional.
     * @param type the {@link TypeES} of this singular attribute.
     * @param bindableType the {@link BindableType} of this singular attribute.
     * @param bindableJavaType the java type that is bound for this singular attribute.
     * @param name the name of this singular attribute.
     * @param ordinal the oridinal of this singular attribute.
     * @param javaType the java type of this singular attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this singular attribute.
     * @param javaMember the java {@link Member} representing this singular attribute.
     * @param readOnly whether or not this singular attribute is read only.
     * @param collection whether or not this singular attribute is a collection.
     * @param association whether or not this singular attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this singular attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this singular attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this singular attribute for a DTO.
     */
    public SingularAttributeESImpl(boolean generated, Generator generator,
            boolean id, boolean version, boolean optional, TypeES<T> type,
            BindableType bindableType, Class<T> bindableJavaType, String name,
            int ordinal, Class<T> javaType,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES<X> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(name, ordinal, javaType, persistentAttributeType, javaMember,
                readOnly, collection, association, declaringType,
                hasValueChangeConstructor, hasValueDtoConstructor);
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

    /**
     * Sets the {@code value} onto the {@code hasValue}. For basic or embeddable values, the actual value is set.
     * For identifiables, the {@link Id}-value is set.
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
            case ENTITY :
            case MAPPED_SUPERCLASS : {
                //Type is a entity or mapped superclass which is identifiable, so casting should be safe.
                //Wrap the id of the identifiable.
                IdentifiableTypeESImpl identifiableType = (IdentifiableTypeESImpl)valueType;
                final Object id;
                if(value == null){
                    id = null;
                } else {
                    id = identifiableType.getId().getJavaMember().get(value);
                }
                hasValue.setValue(id);
                break;
            }

            default : throw new UnsupportedOperationException(
                    "unkown PersistenceType " + persistenceType + " for " + valueType
            );
        }
    }

    @Override
    public HasValue wrapForChange(T value, DtoType dtoType) {
        if (dtoType != DtoType.MANAGED_CLASS) {
            throw new UnsupportedOperationException("Currently only " + DtoType.MANAGED_CLASS + " is supported.");
        }
        final HasValue hv = this.getHasValueChangeConstructor().invoke();
        setSingularValueForChange(hv, getType(), value);
        return hv;
    }

    @Override
    public HasValue wrapForDTO(T value, DtoType dtoType) {
        if (dtoType != DtoType.MANAGED_CLASS) {
            throw new UnsupportedOperationException("Currently only " + DtoType.MANAGED_CLASS + " is supported.");
        }
        switch(getType().getPersistenceType()){
            case EMBEDDABLE:
            case ENTITY: //Should be entity.
                //Return HasValue DTO
                break;
            case BASIC:
            default: {
                //Return HasValue T
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}