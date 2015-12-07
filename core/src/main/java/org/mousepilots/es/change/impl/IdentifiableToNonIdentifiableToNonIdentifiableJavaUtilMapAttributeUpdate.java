package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.impl.HasValueEntry;

/**
 * @author Roy Cleven
 */
public final class IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate<I extends Serializable, VE extends Serializable, K, V> extends IdentifiableJavaUtilMapAttributeUpdate<I, VE, K, V> {

    public IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate() {
    }

    public IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate(List<HasValueEntry<K, V>> additions, List<HasValueEntry<K, V>> removals, AttributeES attribute, VE version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
        super(additions, removals, attribute, version, id, type, dtoType);
    }
    
    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}
