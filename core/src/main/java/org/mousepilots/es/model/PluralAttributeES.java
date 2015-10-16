package org.mousepilots.es.model;

import javax.persistence.metamodel.PluralAttribute;

/**
 * @project EntityScenario
 * @author clevenro
 */
public interface PluralAttributeES<X, C, E> extends AttributeES<X, C>, PluralAttribute<X, C, E> {

}
