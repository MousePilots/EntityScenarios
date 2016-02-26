package org.mousepilots.es.core.model;

import java.util.Set;
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
 * @version 1.0, 7-12-2015
 */
public interface TypeES<T> extends Type<T>, Comparable<TypeES>, HasOrdinal{

    /**
    * @return the original JPA static meta-model class for the represented class
    */
    Class<?> getMetamodelClass();

    /**
     * Gets the type for {@code this.getJavaType().getSuperclass()} if existent, otherwise {@code null}
     * @return the super type.
     */
    TypeES<? super T> getSuperType();

    /**
     * The {@link Set} with elements {@code s} such that {@code s.getJavaType()} is a superclass of {@code this.getJavaType()}, including {@code this}. 
     * The returned {@link Set} includes {@code this}
     * @return this type's super-types, such that {@code s.getJavaType()} is a superclass of {@code this.getJavaType()}
     */
    SortedSet<TypeES<? super T>> getSuperTypes();


    /**
     * @return this type's sub-types {@code s}, such that {@code s.getJavaType()} is a subclass of {@code this.getJavaType()}, including {@code this}.
     */
    SortedSet<TypeES<? extends T>> getSubTypes();
    
    /**
     * Accepts a {@link TypeVisitor}.
     * @param <R> {@code v}'s return type
     * @param <A> {@code v}'s argument type
     * @param v the {@link TypeVisitor}
     * @param arg the argument passed to {@code v}
     * @return the value returned by {@code v.visit(this,arg)}.
     */
    <R,A> R accept(TypeVisitor<R,A> v, A arg);
    
}