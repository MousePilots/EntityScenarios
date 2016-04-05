package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Descriptor class of the {@link javax.persistence.metamodel.EmbeddableType} type of JPA
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public final class EmbeddableTypeDescriptor extends ManagedTypeDescriptor {

    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return EmbeddableTypeESImpl.class;
    }

    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return EmbeddableTypeES.class;
    }    
    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     */
    public EmbeddableTypeDescriptor(
            Class<?> metaModelClass,
            String name, 
            Class javaType, 
            int ordinal){
        super(metaModelClass, Type.PersistenceType.EMBEDDABLE, name, javaType, ordinal);
    }
}