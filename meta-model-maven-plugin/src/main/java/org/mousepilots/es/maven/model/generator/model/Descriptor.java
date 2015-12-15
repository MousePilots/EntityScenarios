package org.mousepilots.es.maven.model.generator.model;

import javax.persistence.metamodel.Type;

/**
 * Generic descriptor that holds the common attributes for all sub descriptors.
 *
 * @author Jurjen van Geenen
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 * @param <T> The kind of the persistence type this descriptor models. Either
 * {@link javax.persistence.metamodel.Type.PersistenceType} or
 * {@link javax.persistence.metamodel.Attribute.PersistentAttributeType}.
 */
public abstract class Descriptor<T> implements Comparable<Descriptor> {

    private Descriptor<T> superDescriptor;
    private T persistenceType;
    private final String name;
    private final Class javaType;
    protected final int ordinal;

    /**
     * Create a new instance of this class.
     * @param persistenceType the persistence type of this descriptor.
     * @param name the name of this descriptor.
     * @param javaType the java type this descriptor represents.
     * @param ordinal the ordinal for this descriptor.
     */
    protected Descriptor(T persistenceType, String name, Class javaType,
            int ordinal) {
        this.persistenceType = persistenceType;
        this.name = name;
        this.javaType = javaType;
        this.ordinal = ordinal;
    }

    /**
     * Create a new instance of this class.
     * @param name the name of this descriptor.
     * @param javaType the java type this descriptor represents.
     * @param ordinal the ordinal for this descriptor.
     */
    protected Descriptor(String name, Class javaType, int ordinal) {
        this(null, name, javaType, ordinal);
    }

    /**
     * @return the {@link Type.PersistenceType} of this descriptor.
     */
    public T getPersistenceType() {
        return persistenceType;
    }

    /**
     * Set the {@link Type.PersistenceType} of this descriptor.
     * @param persistenceType the new persistence type.
     */
    protected void setPersistenceType(T persistenceType) {
        this.persistenceType = persistenceType;
    }

    /**
     * @return the name of this descriptor.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the java type of this descriptor.
     */
    public Class getJavaType() {
        return javaType;
    }

    /**
     * @return the simple name of the {@code javaType}.
     */
    public String getJavaTypeSimpleName() {
        return javaType.getSimpleName();
    }

    /**
     * @return the ordinal of this descriptor.
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * @return the (full) class name of the {@link AbstractType} implementation of this
     * descriptor
     */
    public String getDescriptorClassSimpleName() {
        return getJavaTypeSimpleName() + "_ES";
    }

    /**
     * @return the (canonical) class name of the {@link AbstractType}} implementation of this descriptor.
     */
    public String getDescriptorClassFullName() {
        return getJavaTypeCanonicalName() + "_ES";
    }

    /**
     * @return the (canonical) class name of the {@code javeType} of this descriptor.
     */
    public String getJavaTypeCanonicalName() {
        return javaType.getCanonicalName();
    }

    /**
     * @return the name of the package the represented {@code javaType} is in.
     */
    public String getPackageName() {
        return javaType.getPackage().getName();
    }

    /**
     * @return the super {@link Descriptor} of this descriptor if any.
     */
    public Descriptor<T> getSuperDescriptor() {
        return superDescriptor;
    }

    /**
     * Set the super {@link Descriptor} of this descriptor.
     * @param superDescriptor the new super descriptor.
     */
    public void setSuperDescriptor(Descriptor<T> superDescriptor) {
        this.superDescriptor = superDescriptor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.ordinal;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Descriptor< ?> other = (Descriptor< ?>) obj;
        if (this.ordinal != other.ordinal) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "Descriptor: " + getName() + ", Persistence type: "
                + getPersistenceType() + ", Java type: "
                + getJavaTypeSimpleName() + ", Ordinal: " + getOrdinal();
    }

    @Override
    public int compareTo(Descriptor o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    /**
     * Get a string representation on how to initialise this attribute
     * descriptor.
     *
     * @return a string to initialise this attribute descriptor to insert into
     * the velocity generator.
     */
    public abstract String getStringRepresentation();
}