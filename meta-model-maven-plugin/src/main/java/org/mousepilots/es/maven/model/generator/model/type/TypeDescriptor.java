package org.mousepilots.es.maven.model.generator.model.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.maven.model.generator.model.Descriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.plugin.ReflectionUtils;

/**
 * Descriptor of the {@link javax.persistence.metamodel.Type} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class TypeDescriptor extends Descriptor<PersistenceType> {

    private static final Set<TypeDescriptor> INSTANCES = new TreeSet<>();
    private final Class<?> metaModelClass;
    private Collection<TypeDescriptor> subTypes;

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     */
    public TypeDescriptor(Class<?> metaModelClass,
            PersistenceType persistenceType, String name, Class javaType,
            int ordinal) {
        super(persistenceType, name, javaType, ordinal);
        this.metaModelClass = metaModelClass;
        subTypes = new ArrayList<>();
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
        final Descriptor<PersistenceType> superDescriptor = getSuperDescriptor();
        return superDescriptor == null ? null : superDescriptor.getDescriptorClassFullName();
    }

    public Class<?> getMetaModelClass() {
        return metaModelClass;
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
     * Searches the declared attributes for an attribute with the specified {@code name}.
     * If it is not found it will search in the super classes.
     * @param name the name of the attribute to get.
     * @return the {@link AttributeDescriptor} with the specified {@code name} or {@code null} if the attribute was not found.
     */
    public AttributeDescriptor getAttribute(String name){
        return null;
    }

    /**
     * Get the super descriptor if this descriptor if any.
     * @return the super descriptor of this type, or {@code null} if this type has no super descriptor.
     */
    public TypeDescriptor getSuper(){
        return getInstance(getJavaType().getSuperclass());
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

    /**
     * Check if this type is instantiable.
     * @return {@code true} if this type can be instantiated, or {@code false} otherwise.
     */
    public boolean isInstantiable(){
        return ReflectionUtils.isInstantiable(getJavaType());
    }

    @Override
    public String getStringRepresentation() {
        return "";
    }

    public Collection<TypeDescriptor> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(Collection<TypeDescriptor> subTypes) {
        this.subTypes = subTypes;
    }
}