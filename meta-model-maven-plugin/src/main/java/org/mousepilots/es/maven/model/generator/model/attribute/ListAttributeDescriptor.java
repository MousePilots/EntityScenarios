package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor for the {@link javax.persistence.metamodel.ListAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class ListAttributeDescriptor extends PluralAttributeDescriptor {

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this list attribute.
     * @param name the name of this list attribute.
     * @param ordinal the ordinal of this list attribute.
     * @param javaType the java type of this list attribute.
     */
    public ListAttributeDescriptor(TypeDescriptor elementType, String name,
            Class javaType, int ordinal) {
        super(CollectionType.LIST, elementType, name, javaType, ordinal);
    }
}