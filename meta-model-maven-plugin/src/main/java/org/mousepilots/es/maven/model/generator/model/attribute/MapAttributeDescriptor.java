package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.MapAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class MapAttributeDescriptor extends PluralAttributeDescriptor {

    private final Class keyJavaType;

    public MapAttributeDescriptor(PluralAttribute.CollectionType collectionType, TypeDescriptor elementType, String name, Class keyJavaType, Class valueJavaType, int ordinal) {
        super(PluralAttribute.CollectionType.MAP, elementType, name, valueJavaType, ordinal);
        this.keyJavaType=keyJavaType;
    }

    public Class getKeyJavaType() {
        return keyJavaType;
    }

    public TypeDescriptor getKeyType(){
        return TypeDescriptor.getInstance(getKeyJavaType());
    }
}