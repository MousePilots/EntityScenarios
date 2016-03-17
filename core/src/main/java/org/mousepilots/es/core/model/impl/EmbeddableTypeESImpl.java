package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.model.impl.container.EmbeddableContainer;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The type that is embeddable.
 */
public class EmbeddableTypeESImpl<T> extends ManagedTypeESImpl<T> implements EmbeddableTypeES<T> {

    /**
      * @param ordinal the ordinal of this embeddable type.
      * @param javaType the java type for this embeddable type.
      * @param javaTypeConstructor the zero-arg constructor for the
      * {@code javaType} if existent, otherwise {@code null}
      * @param proxyType the {@link Proxy}-type for the {@code javaType}
      * @param proxyTypeConstructor the zero-arg constructor for the
      * {@code proxyType} if existent, otherwise {@code null}
      * @param hasValueConstructor the value of hasValueConstructor
      * @param metamodelClass the JPa meta model class for this embeddable type.
      * @param attributeOrdinals the singular attributes that are part of this
      * embeddable type.
      * @param superTypeOrdinal the supertype of this embeddable type.
      * @param subTypeOrdinals a set of sub types for this embeddable type.
      * @param associationOrdinals
      */
     public EmbeddableTypeESImpl(
          int ordinal,
          Class<T> javaType,
          Class<?> metamodelClass,
          int superTypeOrdinal,
          Collection<Integer> subTypeOrdinals,
          Constructor<? extends HasValue<T>> hasValueConstructor,
          Constructor<T> javaTypeConstructor,
          Constructor<? extends Proxy<T>> proxyTypeConstructor,
          Class<? extends Proxy<T>> proxyType,
          Set<Integer> attributeOrdinals,
          Collection<Integer> associationOrdinals) {
          super(ordinal, javaType, metamodelClass, superTypeOrdinal, subTypeOrdinals, hasValueConstructor, javaTypeConstructor,
                proxyTypeConstructor, proxyType, attributeOrdinals, associationOrdinals);
     }
     
     @Override
    public PersistenceType getPersistenceType(){
        return PersistenceType.EMBEDDABLE;
    }
}
