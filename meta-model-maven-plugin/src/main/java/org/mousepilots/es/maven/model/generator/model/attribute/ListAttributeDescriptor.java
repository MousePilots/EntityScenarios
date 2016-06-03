package org.mousepilots.es.maven.model.generator.model.attribute;

import java.util.List;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.ListAttributeESImpl;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;
import org.mousepilots.es.maven.model.generator.plugin.PropertyDefinition;

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
            Class javaType, int ordinal, PropertyDefinition customDefinition) {
        super(elementType, name, javaType, ordinal, customDefinition);
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.LIST;
    }

    @Override
    public Class getJavaType() {
        return List.class;
    }
    
    
    

    @Override
    public Class<? extends AttributeES> getDeclaredClass() {
        return ListAttributeES.class;
    }

    @Override
    public Class<? extends AttributeESImpl> getImplementationClass() {
        return ListAttributeESImpl.class;
    }
    
    @Override
    public <I, O> O accept(AttributeDescriptorVisitor<I, O> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
    
}