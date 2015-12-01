package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor for the {@link javax.persistence.metamodel.EntityType} of JPA
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class EntityTypeDescriptor extends IdentifiableTypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     */
    public EntityTypeDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.ENTITY, name, javaType, ordinal);
    }
}