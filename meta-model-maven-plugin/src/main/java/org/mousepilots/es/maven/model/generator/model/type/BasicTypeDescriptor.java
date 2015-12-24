package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.maven.model.generator.plugin.DescriptorUtils;

/**
 * Descriptor for the {@link javax.persistence.metamodel.BasicType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class BasicTypeDescriptor extends TypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this basic type.
     * @param ordinal the ordinal of this basic type.
     * @param javaType the java type of this basic type.
     */
    public BasicTypeDescriptor(Class<?> metaModelClass,
            String name, Class javaType, int ordinal) {
        super(metaModelClass, Type.PersistenceType.BASIC, name, javaType, ordinal);
    }

    @Override
    public String getStringRepresentation() {
        return DescriptorUtils.printBasicTypeImpl(this);
    }
}