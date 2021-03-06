/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mousepilots.es.core.util.Procedure;


/**
 * Abstract base for observable {@link Collection}, {@link List},{@link Set}, {@link Map}.
 * @author geenenju
 * @param <D> the observed subject's type
 * @param <O> the observer type
 */
public abstract class Observable<D,O extends Observer> implements Serializable{
     
     private ArrayList<O> listeners = new ArrayList<>();
     
     private D delegate;
     
     protected Observable(){}
     
     protected Observable(D delegate) {
          this.delegate = delegate;
     }
     
     protected abstract D createUnmodifiable(D delegate);
     
     public void addListener(O listener){
          listeners.add(listener);
     }
     
     public void removeListener(O listener){
          listeners.remove(listener);
     }
     
     protected void fire(Procedure<O> p){
          for(O listener : listeners){
               p.apply(listener);
          }
     }

    /**
     * @return the delegate
     */
    public D getDelegate() {
        return delegate;
    }
     
     
}
