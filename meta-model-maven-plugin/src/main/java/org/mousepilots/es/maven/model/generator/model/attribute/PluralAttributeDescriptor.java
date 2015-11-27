package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.Bindable.BindableType;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.PluralAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class PluralAttributeDescriptor extends AttributeDescriptor{

    private final CollectionType collectionType;
    private final TypeDescriptor elementType;

    /**
     * Create a new instance of this class.
     * @param collectionType the type of collection this plural attribute represents.
     * @param elementType the type of the elements for this plural attribute.
     * @param name the name of this plural attribute.
     * @param ordinal the ordinal of this plural attribute.
     * @param javaType the java type of this plural attribute.
     */
    public PluralAttributeDescriptor(CollectionType collectionType,
            TypeDescriptor elementType, String name, Class javaType,
            int ordinal) {
        super(name, javaType, ordinal);
        this.collectionType = collectionType;
        this.elementType = elementType;
    }

    /**
     * Get the {@link CollectionType} that this plural attribute represents.
     * @return the collection type.
     */
    public CollectionType getCollectionType() {
        return collectionType;
    }

    /**
     * Get the type of the elements for this plural attribute.
     * @return the element type.
     */
    public TypeDescriptor getElementType() {
        return elementType;
    }

    /**
     * Gets the bindable type for this plural attribute.
     * @return the bindable type.
     */
    public BindableType getBindableType(){
        return BindableType.PLURAL_ATTRIBUTE;
    }

    /**
     * Gets the bindable java type for this plural attribute.
     * @return the bindable java type.
     */
    public Class getBindableJavaType(){
        return elementType.getJavaType();
    }
}