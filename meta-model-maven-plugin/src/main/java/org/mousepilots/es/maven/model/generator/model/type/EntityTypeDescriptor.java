package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor for the {@link javax.persistence.metamodel.EntityType} of JPA
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class EntityTypeDescriptor extends IdentifiableTypeDescriptor {

    public EntityTypeDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.ENTITY, name, javaType, ordinal);
    }
}