/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.Collection;

/**
 * Observes an {@link ObservableCollection}
 * @author geenenju
 * @param <E>
 * @param <C>
 */
public interface CollectionObserver<E,C extends Collection<E>> extends Observer<C>{
     /**
      * Invoked before {@code subject.add(e)}
      * @param subject
      * @param e 
      */
     void onAdd(C subject, E e);
     
     /**
      * Invoked before {@code subject.addAll(addAlls)}
      * @param subject
      * @param addAlls 
      */
     void onAddAll(C subject, Collection addAlls);
     
     /**
      * Invoked before {@code subject.removeAll(c)}
      * @param subject
      * @param c 
      */
     void onRemoveAll(C subject, Collection<?> c);

     /**
      * Invoked before {@code subject.retainAll(c)}
      * @param subject
      * @param c 
      */
     void onRetainAll(C subject, Collection<?> c);
     
     
}
