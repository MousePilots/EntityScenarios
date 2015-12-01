package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.model.AttributeES;

/**
 * @author Roy Cleven
 */
public final class IdentifiableToIdentifiableSingularAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends IdentifiableSingularAssociationAttributeUpdate<I, V, A> {

    public IdentifiableToIdentifiableSingularAssociationAttributeUpdate() {
    }

    public IdentifiableToIdentifiableSingularAssociationAttributeUpdate(AttributeES attribute, I id, V version, A oldValue, A newValue) {
        super(attribute, id, version, oldValue, newValue);
    }
    
    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
    

}
