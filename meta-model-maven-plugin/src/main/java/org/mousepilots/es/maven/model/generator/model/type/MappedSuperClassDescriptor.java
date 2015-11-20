package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor of the {@link javax.persistence.metamodel.MappedSuperclassType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class MappedSuperClassDescriptor extends IdentifiableTypeDescriptor {

    public MappedSuperClassDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.MAPPED_SUPERCLASS, name, javaType, ordinal);
    }
}