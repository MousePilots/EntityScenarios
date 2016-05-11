/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

/**
 *
 * @author ap34wv
 */
public final class HasCharacter extends AbstractHasValue<Character>{
    
    private Character value;

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public void setValue(Character value) {
        this.value = value;
    }
    
    
    
    
}
