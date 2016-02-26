package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import org.mousepilots.es.core.change.impl.container.EmbeddableContainer;
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

     public EmbeddableTypeESImpl(
          int ordinal,
          Class<T> javaType,
          Constructor<T> javaTypeConstructor,
          Class<? extends Proxy<T>> proxyType,
          Constructor<? extends Proxy<T>> proxyTypeConstructor,
          Constructor<? extends HasValue<T>> hasValueConstructor,
          PersistenceType persistenceType,
          Class<?> metamodelClass,
          Set<Integer> attributeOrdinals,
          int superTypeOrdinal,
          Collection<Integer> subTypeOrdinals) {
          super(ordinal, javaType, javaTypeConstructor, proxyType, proxyTypeConstructor, hasValueConstructor, persistenceType, metamodelClass, attributeOrdinals, superTypeOrdinal, subTypeOrdinals);

     }
}
