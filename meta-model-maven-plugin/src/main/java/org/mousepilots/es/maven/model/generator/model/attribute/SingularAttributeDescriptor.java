package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.Bindable;

/**
 * Descriptor of the {@link javax.persistence.metamodel.SingularAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class SingularAttributeDescriptor extends AttributeDescriptor {

    public SingularAttributeDescriptor(String name, Class javaType, int ordinal) {
        super(name, javaType, ordinal);
    }

    public Bindable.BindableType getBindableType(){
        return Bindable.BindableType.SINGULAR_ATTRIBUTE;
    }

    public Class getBindableJavaType(){
        return getJavaType();
    }
}