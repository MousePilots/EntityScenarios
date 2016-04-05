/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

/**
 *
 * @author geenenju
 */
public class LazyValue<T> {
    
    private final Producer<T> valueProducer;

    public LazyValue(Producer<T> valueProducer) {
        this.valueProducer = valueProducer;
    }
    private boolean inialized = false;
    
    private T value;

    public final T getValue(){
        if(!inialized){
            this.value = valueProducer.produce();
            inialized = true;
        }
        return value;
    }
}
