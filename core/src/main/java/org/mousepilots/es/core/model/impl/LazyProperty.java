/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

/**
 *
 * @author jgeenen
 */
public class LazyProperty<T> {
    
    public static interface Initializer<T>{
        
        void init(LazyProperty<T> lazyProperty);
    };
    
    private final Initializer<T> initializer;
    private boolean initalized=false;
    private T value;

    public LazyProperty(Initializer<T> initializer) {
        this.initializer = initializer;
    }

    public T getValue(){
        if(!initalized){
            initializer.init(this);
            initalized=true;
        }
        return value;
    }
    
    protected void setValue(T value){

    }
    
}
