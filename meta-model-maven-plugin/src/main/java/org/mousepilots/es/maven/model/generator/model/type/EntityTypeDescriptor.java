package org.mousepilots.es.maven.model.generator.model.type;

import java.util.Map;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.util.StringUtils;

/**
 * Descriptor for the {@link javax.persistence.metamodel.EntityType} of JPA
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public final class EntityTypeDescriptor extends IdentifiableTypeDescriptor {
    
    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return EntityTypeESImpl.class;
    }

    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return EntityTypeES.class;
    }

    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Map<String, String> cp2v = super.getConstructorParameterToValue();
        cp2v.put("name", StringUtils.quote(getName()));
        return cp2v;
    }
    
    
    
    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     */
    public EntityTypeDescriptor(
            Class<?> metaModelClass,
            String name, 
            Class javaType, 
            int ordinal){
        super(metaModelClass, Type.PersistenceType.ENTITY, name, javaType, ordinal);
    }
}