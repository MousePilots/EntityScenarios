package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.SetAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class SetAttributeDescriptor extends PluralAttributeDescriptor{

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this list attribute.
     * @param name the name of this list attribute.
     * @param ordinal the ordinal of this list attribute.
     * @param javaType the java type of this list attribute.
     */
    public SetAttributeDescriptor(TypeDescriptor elementType, String name,
            Class javaType, int ordinal) {
        super(CollectionType.SET, elementType, name, javaType, ordinal);
    }
}