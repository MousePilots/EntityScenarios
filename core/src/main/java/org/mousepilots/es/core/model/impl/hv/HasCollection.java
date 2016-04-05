/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.Collection;

/**
 *
 * @author jgeenen
 */
public final class HasCollection extends AbstractHasCollection<Collection>{

    public HasCollection() {
    }

    @Override
    protected Collection createEmpty() {
        return createEmptyList();
    }

    
}
