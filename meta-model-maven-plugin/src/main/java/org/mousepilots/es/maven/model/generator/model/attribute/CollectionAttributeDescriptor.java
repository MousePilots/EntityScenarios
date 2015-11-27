package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.CollectionAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class CollectionAttributeDescriptor extends PluralAttributeDescriptor {

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this collection attribute.
     * @param name the name of this collection attribute.
     * @param ordinal the ordinal of this collection attribute.
     * @param javaType the java type of this collection attribute.
     */
    public CollectionAttributeDescriptor(TypeDescriptor elementType,
            String name, Class javaType, int ordinal) {
        super(CollectionType.COLLECTION, elementType, name, javaType, ordinal);
    }
}