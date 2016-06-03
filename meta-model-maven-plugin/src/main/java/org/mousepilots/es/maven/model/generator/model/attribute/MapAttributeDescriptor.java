package org.mousepilots.es.maven.model.generator.model.attribute;

import java.util.Map;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;
import org.mousepilots.es.maven.model.generator.plugin.PropertyDefinition;

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
            Class keyJavaType, Class valueJavaType, int ordinal, PropertyDefinition customDefinition) {
        super(elementType, name, valueJavaType, ordinal, customDefinition);
        this.keyJavaType = keyJavaType;
    }

    @Override
    public Class getJavaType() {
        return Map.class;
    }

    
    
    @Override
    public CollectionType getCollectionType() {
        return CollectionType.MAP;
    }

    @Override
    public String getGenericsString(){
        final String[] parts = super.getGenericsString().split(",");
        return parts[0] + "," + getKeyJavaType().getCanonicalName() + "," + parts[1];
    }

    
    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Map<String, String> cp2v = super.getConstructorParameterToValue();
        cp2v.put("keyTypeOrdinal", getKeyType().getOrdinal().toString());
        cp2v.put("keyAssociation", getAssociationInstantiation(AssociationTypeES.KEY));
        return cp2v;
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

    @Override
    public Class<? extends AttributeES> getDeclaredClass() {
        return MapAttributeES.class;
    }

    @Override
    public Class<? extends AttributeESImpl> getImplementationClass() {
        return MapAttributeESImpl.class;
    }
    
    @Override
    public <I, O> O accept(AttributeDescriptorVisitor<I, O> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
}