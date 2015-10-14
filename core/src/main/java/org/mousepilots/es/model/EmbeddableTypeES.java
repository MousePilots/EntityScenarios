package org.mousepilots.es.model;

import javax.persistence.metamodel.EmbeddableType;

/**
 * @project EntityScenario
 * @author clevenro
 */
public interface EmbeddableTypeES<T> extends EmbeddableType<T>,ManagedTypeES<T> {

}
