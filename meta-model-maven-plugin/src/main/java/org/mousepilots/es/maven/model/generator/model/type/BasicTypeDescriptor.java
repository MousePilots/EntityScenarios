package org.mousepilots.es.maven.model.generator.model.type;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Descriptor for the {@link javax.persistence.metamodel.BasicType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class BasicTypeDescriptor extends TypeDescriptor {

    private final String basePackage;
    
    /**
     * Create a new instance of this class.
     * @param basePackage
     * @param name the name of this basic type.
     * @param ordinal the ordinal of this basic type.
     * @param javaType the java type of this basic type.
     */
    public BasicTypeDescriptor(
            String basePackage,
            String name, 
            Class javaType, 
            int ordinal){
        super(Type.PersistenceType.BASIC, name, javaType, ordinal);
        this.basePackage = basePackage;
    }

    @Override
    public final String getPackageName() {
        final String packageName = super.getPackageName();
        return basePackage==null ? packageName : basePackage + "." + packageName;
    }

    @Override
    public String getDescriptorClassFullName() {
        return getPackageName() + "." + getJavaTypeSimpleName() + DESCRIPTOR_CLASS_SUFFIX;
    }

    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return BasicTypeES.class;
    }

    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return BasicTypeESImpl.class;
    }

    
}