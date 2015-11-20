package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor class of the {@link javax.persistence.metamodel.EmbeddableType} type of JPA
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class EmbeddableTypeDescriptor extends ManagedTypeDescriptor {

    public EmbeddableTypeDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.EMBEDDABLE, name, javaType, ordinal);
    }
}