package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;

/**
 * Descriptor for the {@link javax.persistence.metamodel.BasicType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class BasicTypeDescriptor extends TypeDescriptor {

    public BasicTypeDescriptor(String name, Class javaType, int ordinal) {
        super(Type.PersistenceType.BASIC, name, javaType, ordinal);
    }
}