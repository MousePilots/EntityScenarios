/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

import java.util.Collection;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <C>
 */
public interface CollectionObserver<E,C extends Collection<E>> extends Observer<C>{
     
     void onAdd(C subject, E e);
     
     void onAddAll(C subject, Collection addAlls);
     
     void onRemoveAll(C subject, Collection<?> c);
     
     void onRetainAll(C subject, Collection<?> c);
     
     
}
