package org.mousepilots.es.maven.model.generator.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic descriptor that holds the common attributes for all sub descriptors.
 *
 * @author Jurjen van Geenen
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 * @param <T> The kind of the persistence type this descriptor models. Either
 * {@link javax.persistence.metamodel.Type.PersistenceType} or
 * {@link javax.persistence.metamodel.Attribute.PersistentAttributeType}.
 */
public abstract class Descriptor<T> implements Comparable<Descriptor> {

    private static final Date currentDate = new Date();
    private final String esNameAndVersion;
    private Descriptor<T> superDescriptor;
    private T persistenceType;
    private final String name;
    private final Class javaType;
    protected final int ordinal;

    protected Descriptor(T persistenceType, String name, Class javaType,
            int ordinal) {
        this.esNameAndVersion = getESNameAndVersion();
        this.persistenceType = persistenceType;
        this.name = name;
        this.javaType = javaType;
        this.ordinal = ordinal;
    }

    protected Descriptor(String name, Class javaType, int ordinal) {
        this(null, name, javaType, ordinal);
    }

    public String getEsNameAndVersion() {
        return esNameAndVersion;
    }

    public T getPersistenceType() {
        return persistenceType;
    }

    protected void setPersistenceType(T persistenceType) {
        this.persistenceType = persistenceType;
    }

    public String getName() {
        return name;
    }

    public Class getJavaType() {
        return javaType;
    }

    public String getJavaTypeSimpleName() {
        return javaType.getSimpleName();
    }

    public int getOrdinal() {
        return ordinal;
    }

    /**
     * the (full) class name of the {@link AbstractType} implementation of this
     * descriptor
     *
     * @return
     */
    public String getDescriptorClassSimpleName() {
        return getJavaTypeSimpleName() + "_ES";
    }

    public String getDescriptorClassFullName() {
        return getJavaTypeCanonicalName() + "_ES";
    }

    public String getJavaTypeCanonicalName() {
        return javaType.getCanonicalName();
    }

    public String getPackageName() {
        return javaType.getPackage().getName();
    }

    public static Date getCurrentDate() {
        return currentDate;
    }

    public Descriptor<T> getSuperDescriptor() {
        return superDescriptor;
    }

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
        return getClass().getSimpleName();
    }

    @Override
    public int compareTo(Descriptor o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    /**
     * Load the name of the plugin and its version from the properties file.
     * So it can be used to annotate the generated meta model classes.
     * @return A {@link String} containing the name and version of the plugin.
     */
    private String getESNameAndVersion() {
        final Properties properties = new Properties();
        final StringBuilder sb = new StringBuilder();
        try {
            InputStream resourceAsStream = Descriptor.class.getResourceAsStream(
                    "/project.properties");
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                sb.append(properties.getProperty("artifactId", "Unknown"));
                sb.append("_");
                sb.append(properties.getProperty("version", "unknown version"));
            } else {
                sb.append("Unknown_unknown version");
            }
        } catch (IOException ex) {
            Logger.getLogger(Descriptor.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return sb.toString();
    }
}