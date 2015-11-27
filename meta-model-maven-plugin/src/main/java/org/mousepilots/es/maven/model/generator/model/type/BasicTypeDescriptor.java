package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor for the {@link javax.persistence.metamodel.BasicType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class BasicTypeDescriptor extends TypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param name the name of this basic type.
     * @param ordinal the ordinal of this basic type.
     * @param javaType the java type of this basic type.
     */
    public BasicTypeDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.BASIC, name, javaType, ordinal);
    }
}