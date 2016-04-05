/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jgeenen
 */
public final class HasSet extends AbstractHasCollection<Set>{
    
    public HasSet(){
        
    }

    @Override
    protected Set createEmpty() {
        return new HashSet<>();
    }
    
}
