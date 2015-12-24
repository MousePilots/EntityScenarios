package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor class of the {@link javax.persistence.metamodel.EmbeddableType} type of JPA
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class EmbeddableTypeDescriptor extends ManagedTypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     */
    public EmbeddableTypeDescriptor(Class<?> metaModelClass,
            String name, Class javaType, int ordinal) {
        super(metaModelClass, Type.PersistenceType.EMBEDDABLE, name, javaType, ordinal);
    }
}