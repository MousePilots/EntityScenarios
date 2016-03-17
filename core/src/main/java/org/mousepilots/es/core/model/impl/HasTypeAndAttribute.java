/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.TypeES;

/**
 *
 * @author jgeenen
 * @param <T>
 * @param <A>
 */
public class HasTypeAndAttribute<T extends TypeES,A extends AttributeES> extends HasTypeImpl<T>{
    
    private int attributeOrdinal;
    
    public A getAttribute(){
        return (A) AbstractMetamodelES.getInstance().getAttribute(attributeOrdinal);
    }

    public HasTypeAndAttribute() {
        super();
    }

    public HasTypeAndAttribute(int attributeOrdinal, int typeOrdinal) {
        super(typeOrdinal);
        this.attributeOrdinal = attributeOrdinal;
    }
    
    
}
