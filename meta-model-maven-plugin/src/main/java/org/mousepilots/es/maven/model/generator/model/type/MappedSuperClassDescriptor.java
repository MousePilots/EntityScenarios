package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor of the {@link javax.persistence.metamodel.MappedSuperclassType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class MappedSuperClassDescriptor extends IdentifiableTypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param name the name of this identifiable type.
     * @param ordinal the ordinal of this identifiable type.
     * @param javaType the java type of this identifiable type.
     */
    public MappedSuperClassDescriptor(String name, Class javaType,
            int ordinal) {
        super(Type.PersistenceType.MAPPED_SUPERCLASS, name, javaType, ordinal);
    }
}