/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import org.mousepilots.es.core.model.HasValue;

/**
 *
 * @author geenenju
 */
public class HasNumber implements HasValue<Number>{
    
    private Number value;

    @Override
    public Number getValue() {
        return value;
    }

    @Override
    public void setValue(Number value) {
        this.value = value;
    }
    
}
