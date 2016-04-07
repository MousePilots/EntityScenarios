package org.mousepilots.es.core.model;

import javax.persistence.metamodel.EmbeddableType;

/**
 * Instances of the type {@link EmbeddableTypeES} represent embeddable types.
 *
 * @param <T> The type that is embeddable.
 * @see EmbeddableType
 * @see ManagedTypeES
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface EmbeddableTypeES<T> extends EmbeddableType<T>, ManagedTypeES<T> {
}
