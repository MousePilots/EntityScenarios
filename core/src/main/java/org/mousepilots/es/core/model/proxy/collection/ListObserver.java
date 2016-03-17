/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author jgeenen
 */
public interface ListObserver<E> extends CollectionObserver<E, List<E>>{
    
    void onSet(List<E> subject, int index, E element);
    
    void onAdd(List<E> subject, int index, E element);
    
    void onAddAll(List<E> subject, int index, Collection<? extends E> c);
    
    void onRemove(List<E> subject, int index);
    
}
