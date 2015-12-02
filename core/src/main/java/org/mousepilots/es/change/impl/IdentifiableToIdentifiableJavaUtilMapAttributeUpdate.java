package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;

/**
 * @author Roy Cleven
 */
public final class IdentifiableToIdentifiableJavaUtilMapAttributeUpdate<I extends Serializable, VE extends Serializable, K, V> extends JavaUtilMapAttributeUpdate<I, VE, K, V> {

    public IdentifiableToIdentifiableJavaUtilMapAttributeUpdate() {
    }

    public IdentifiableToIdentifiableJavaUtilMapAttributeUpdate(AttributeES attribute, I id, VE version, Collection<AbstractMap.SimpleEntry<K, V>> additions, Collection<AbstractMap.SimpleEntry<K, V>> removals, DtoType dtoType) {
        super(attribute, id, version, additions, removals, dtoType);
    }
    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
