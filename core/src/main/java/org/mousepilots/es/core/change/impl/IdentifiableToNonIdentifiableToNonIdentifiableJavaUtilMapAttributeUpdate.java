package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.impl.HasValueEntry;

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
