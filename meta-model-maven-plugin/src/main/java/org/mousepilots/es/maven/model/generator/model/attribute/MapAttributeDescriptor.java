package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.MapAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class MapAttributeDescriptor extends PluralAttributeDescriptor {

    private final Class keyJavaType;

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this map attribute.
     * @param name the name of this map attribute.
     * @param keyJavaType the type of the key for this map attribute.
     * @param valueJavaType the type of the value for this map attribute.
     * @param ordinal the ordinal of this map attribute.
     */
    public MapAttributeDescriptor(TypeDescriptor elementType, String name,
            Class keyJavaType, Class valueJavaType, int ordinal) {
        super(CollectionType.MAP, elementType, name, valueJavaType, ordinal);
        this.keyJavaType = keyJavaType;
    }

    /**
     * Get the java type of the key for this map attribute.
     * @return the type of the key.
     */
    public Class getKeyJavaType() {
        return keyJavaType;
    }

    /**
     * Get the {@link TypeDescriptor} of the key for this map attribute.
     * @return the type descriptor of the key.
     */
    public TypeDescriptor getKeyType(){
        return TypeDescriptor.getInstance(getKeyJavaType());
    }
}