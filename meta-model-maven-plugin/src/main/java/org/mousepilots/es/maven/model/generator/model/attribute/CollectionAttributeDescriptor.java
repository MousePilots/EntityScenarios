package org.mousepilots.es.maven.model.generator.model.attribute;

import java.util.Collection;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.CollectionAttributeESImpl;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.CollectionAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class CollectionAttributeDescriptor extends PluralAttributeDescriptor {

    @Override
    public Class<? extends AttributeES> getDeclaredClass() {
        return CollectionAttributeES.class;
    }

    @Override
    public Class<? extends AttributeESImpl> getImplementationClass() {
        return CollectionAttributeESImpl.class;
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.COLLECTION;
    }
    
    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this collection attribute.
     * @param name the name of this collection attribute.
     * @param ordinal the ordinal of this collection attribute.
     * @param javaType the java type of this collection attribute.
     */
    public CollectionAttributeDescriptor(TypeDescriptor elementType,
            String name, Class javaType, int ordinal) {
        super(elementType, name, javaType, ordinal);
    }

    @Override
    public Class getJavaType() {
        return Collection.class;
    }
    
    

    @Override
    public <I, O> O accept(AttributeDescriptorVisitor<I, O> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
}