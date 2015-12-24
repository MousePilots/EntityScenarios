package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.metamodel.Bindable.BindableType;
import org.mousepilots.es.maven.model.generator.plugin.DescriptorUtils;

/**
 * Descriptor of the {@link javax.persistence.metamodel.SingularAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class SingularAttributeDescriptor extends AttributeDescriptor {

    /**
     * Create a new instance of this class.
     * @param name the name of this plural attribute.
     * @param ordinal the ordinal of this plural attribute.
     * @param javaType the java type of this plural attribute.
     */
    public SingularAttributeDescriptor(String name, Class javaType,
            int ordinal) {
        super(name, javaType, ordinal);
    }

    /**
     * Get the {@link BindableType} for this singular attribute.
     * @return the bindable type.
     */
    public BindableType getBindableType(){
        return BindableType.SINGULAR_ATTRIBUTE;
    }

    /**
     * Get the bindable java type for this singular attribute.
     * @return the bindable java type.
     */
    public Class getBindableJavaType(){
        return getJavaType();
    }

    /**
     * Check if this singular attribute is an optional attribute.
     * If this attribute is part of the id or if this attribute is not nullable it is considered to be not optional.
     * @return {@code true} if this attribute is considered to be optional, {@code false} otherwise.
     */
    public boolean isOptional(){
        Column columnAnnotation = getAnnotation(Column.class);
        if (getAnnotation(Id.class) != null || (columnAnnotation != null && !columnAnnotation.nullable())) {
            return false;
        }
        return true;
    }

    @Override
    public String getStringRepresentation(){
        return DescriptorUtils.printSingularAttributeImpl(this);
    }
}