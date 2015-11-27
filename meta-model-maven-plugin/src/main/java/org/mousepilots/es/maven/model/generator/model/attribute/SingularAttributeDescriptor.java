package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.Bindable.BindableType;

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
}