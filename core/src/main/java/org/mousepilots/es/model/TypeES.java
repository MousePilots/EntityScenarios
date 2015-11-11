package org.mousepilots.es.model;

import java.util.SortedSet;
import javax.persistence.metamodel.Type;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * Represents a domain-class annotated as {@link Entity}, {@link MappedSuperclass} or {@link Embeddable}.
 * @see Type
 * @param <T> The type of the represented object or attribute
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface TypeES<T> extends Type<T>, Comparable<TypeES>, HasOrdinal{

    /**
    * @return the fully qualified name of {@code T}'s class
    */
    String getJavaClassName();

    /**
     * @return the unique name for {@code this}
     */
    String getName();

    /**
     * @return {@code True} when the object is instantiable. {@code False} otherwise.
     */
    boolean isInstantiable();

    /**
    * @return a new instance of {@link #getJavaType()}
    * @throws UnsupportedOperationException if {@code !this.isInstantiable()}
    */
    T createInstance();

    /**
    * @return the JPA static meta-model class for the represented class
    */
    Class<? extends Type<T>> getMetamodelClass();

    /**
     * @return this type's super-types {@code s}, such that {@code s.getJavaType()} is a superclass of {@code this.getJavaType()}
     */
    SortedSet<TypeES<? super T>> getSuperTypes();

    /**
     * @return this type's sub-types {@code s}, such that {@code s.getJavaType()} is a subclass of {@code this.getJavaType()}
     */
    SortedSet<TypeES<? extends T>> getSubTypes();
}