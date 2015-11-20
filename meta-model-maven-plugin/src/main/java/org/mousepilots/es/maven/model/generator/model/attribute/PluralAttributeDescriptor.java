package org.mousepilots.es.maven.model.generator.model.attribute;

import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.PluralAttribute;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.PluralAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class PluralAttributeDescriptor extends AttributeDescriptor{

    private final PluralAttribute.CollectionType collectionType;
    private final TypeDescriptor elementType;

    public PluralAttributeDescriptor(PluralAttribute.CollectionType collectionType, TypeDescriptor elementType, String name, Class javaType, int ordinal) {
        super(name, javaType, ordinal);
        this.collectionType = collectionType;
        this.elementType = elementType;
    }

    public PluralAttribute.CollectionType getCollectionType() {
        return collectionType;
    }

    public TypeDescriptor getElementType() {
        return elementType;
    }

    public Bindable.BindableType getBindableType(){
        return Bindable.BindableType.PLURAL_ATTRIBUTE;
    }

    public Class getBindableJavaType(){
        return elementType.getJavaType();
    }
}