package org.mousepilots.es.core.scenario.io;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.scenario.ScenarioGraph;

/**
 * 
 * Serializes persistent data into your domain-model's own classes according to {@link #getScenarioGraph() }. 
 * I.e. a {@code User} object is serialized into a {@code User} copy if the type is readable in the corresponding scenario. 
  * @see Serializer
 * @author jgeenen
 */
public class ManagedTypeSerializer extends Serializer{

    private final AttributeSerializerDelegate<ManagedTypeSerializer> 
            attributeSerializerDelegate = new AttributeSerializerDelegate(this);
    
    private final TypeSerializerDelegate<ManagedTypeSerializer> 
            typeSerializerDelegate = new TypeSerializerDelegate(this);
    
    public ManagedTypeSerializer(ScenarioGraph scenarioGraph) {
        super(scenarioGraph);
    }
    
    @Override
    protected AttributeSerializerDelegate<ManagedTypeSerializer> getAttributeSerializerDelegate() {
        return attributeSerializerDelegate;
    }

    @Override
    protected TypeSerializerDelegate<ManagedTypeSerializer> getTypeSerializerDelegate() {
        return typeSerializerDelegate;
    }

}
