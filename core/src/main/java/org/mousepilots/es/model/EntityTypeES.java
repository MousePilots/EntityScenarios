package org.mousepilots.es.model;

import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;

/**
 *
 * @author clevenro
 */
public interface EntityTypeES<T> extends IdentifiableTypeES<T>, EntityType<T>, BindableES<T> {
    
}
