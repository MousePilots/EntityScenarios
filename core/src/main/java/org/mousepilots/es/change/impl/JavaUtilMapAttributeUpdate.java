package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableChange;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <K>
 * @param <V>
 */
public class JavaUtilMapAttributeUpdate<I extends Serializable, K, V> extends AbstractIdentifiableChange<Serializable>{

    public JavaUtilMapAttributeUpdate(){
        super();
    }
    
    public JavaUtilMapAttributeUpdate(IdentifiableTypeES type, I id){
        super(type,id);
    }
    
    @Override
    public CRUD operation() {
        return CRUD.UPDATE;
    }

    @Override
    public void accept(ChangeVisitor changeHandler) {
        changeHandler.visit(this);
    }
}