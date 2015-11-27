package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor class of the {@link javax.persistence.metamodel.EmbeddableType} type of JPA
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class EmbeddableTypeDescriptor extends ManagedTypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     */
    public EmbeddableTypeDescriptor(String name, Class javaType,
            int ordinal) {
        super(Type.PersistenceType.EMBEDDABLE, name, javaType, ordinal);
    }
}