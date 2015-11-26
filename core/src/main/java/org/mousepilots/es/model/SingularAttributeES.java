package org.mousepilots.es.model;

import java.lang.reflect.Member;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.GeneratedValue;

/**
 * Instances of the type SingularAttribute represents persistent
 * single-valued properties or fields.
 *
 * @param <X> The type containing the represented attribute
 * @param <T> The type of the represented attribute
 * @author Roy Cleven
 * @see AttributeES
 * @see BindableES
 * @see SingularAttribute
 */
public interface SingularAttributeES<X, T> extends AttributeES<X, T,T>, BindableES<T>, SingularAttribute<X, T>{

    /**
    * @return whether or not {@code this} represents an attribute with a {@link GeneratedValue}
    */
    boolean isGenerated();

   /**
    * @return the {@link Generator} if {@link #isGenerated()}, otherwise {@code null}
    */
    Generator getGenerator();

    @Override
    TypeES<T> getType();

}