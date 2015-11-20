package org.mousepilots.es.maven.model.generator.model.type;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.maven.model.generator.model.Descriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.Type} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class TypeDescriptor extends Descriptor<Type.PersistenceType> {

    private static final Set<TypeDescriptor> INSTANCES = new TreeSet<>();

    public TypeDescriptor(Type.PersistenceType persistenceType, String name, Class javaType, int ordinal) {
        super(persistenceType, name, javaType, ordinal);
        INSTANCES.add(this);
    }

    /**
     * @return Get all the current {@link TypeDescriptor}s.
     */
    public static Set<TypeDescriptor> getAll() {
        return INSTANCES;
    }

    /**
     * The fully qualified name of the meta-model class this' meta-model class is a subclass of.
     * @return
     */
    public String getMetaModelSuperClassFullName(){
        final Descriptor<Type.PersistenceType> superDescriptor = getSuperDescriptor();
        return superDescriptor == null ? null : superDescriptor.getDescriptorClassFullName();
    }

    /**
     * Get a specific instance of a descriptor.
     * @param javaClass The class to get a descriptor of.
     * @return A {@link TypeDescriptor} of the specified class, or {@code null} if none was found.
     */
    public static TypeDescriptor getInstance(Class javaClass){
        for (TypeDescriptor typeDescriptor : INSTANCES){
            if (typeDescriptor.getJavaType() == javaClass) {
                return typeDescriptor;
            }
        }
        return null;
    }

    /**
     * Get a specific instance of a descriptor.
     * @param <T> A type that extends {@link TypeDescriptor}.
     * @param javaClass The class to get the descriptor of.
     * @param typeDescriptorClass The type of descriptor you wish to have.
     * @return A descriptor of the specified {@code typeDescriptorClass}, or {@code null} if none was found.
     */
    public static <T extends TypeDescriptor> T getInstance(Class javaClass, Class<T> typeDescriptorClass){
        TypeDescriptor td = getInstance(javaClass);
        if (td != null && typeDescriptorClass.isAssignableFrom(td.getClass())) {
            return (T) td;
        } else {
            return null;
        }
    }
}