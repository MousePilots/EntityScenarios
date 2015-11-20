package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.CollectionAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class CollectionAttributeDescriptor extends PluralAttributeDescriptor {

    public CollectionAttributeDescriptor(PluralAttribute.CollectionType collectionType, TypeDescriptor elementType, String name, Class javaType, int ordinal) {
        super(collectionType, elementType, name, javaType, ordinal);
    }
}