package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.MappedSuperclassTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Descriptor of the {@link javax.persistence.metamodel.MappedSuperclassType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public final class MappedSuperclassDescriptor extends IdentifiableTypeDescriptor {

    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return MappedSuperclassTypeESImpl.class;
    }

    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return MappedSuperclassTypeES.class;
    }       
    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this identifiable type.
     * @param ordinal the ordinal of this identifiable type.
     * @param javaType the java type of this identifiable type.
     */
    public MappedSuperclassDescriptor(Class<?> metaModelClass,
            String name, Class javaType, int ordinal) {
        super(metaModelClass, Type.PersistenceType.MAPPED_SUPERCLASS, name,javaType, ordinal);
    }
}