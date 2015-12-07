package org.mousepilots.es.core.change.impl;

import org.mousepilots.es.core.model.AttributeES;

/**
 * @author Roy Cleven
 */
public class EmbeddableAndAttribute<T> {

    private final T embeddable;
    private final AttributeES attribute;

    public EmbeddableAndAttribute(T embeddable, AttributeES attribute) {
        this.embeddable = embeddable;
        this.attribute = attribute;
    }

    public T getEmbeddable() {
        return embeddable;
    }

    public AttributeES getAttribute() {
        return attribute;
    }   
}