/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <C>
 * @param <O>
 */
abstract class AbstractObservableCollection<E,C extends Collection<E>,O extends CollectionObserver<E,C>> extends Observable<C,O> implements Collection<E>{

     protected AbstractObservableCollection(C delegate) {
          super(delegate);
     }
     
     protected final boolean isDelegateSizeChangedAfterRunning(Runnable r){
         final C delegate = getDelegate();
         final int oldSize = delegate.size();
         r.run();
         return oldSize!=delegate.size();
     }

     @Override
     public int size() {
          return getDelegate().size();
     }

     @Override
     public boolean isEmpty() {
          return getDelegate().isEmpty();
     }

     @Override
     public boolean contains(Object o) {
          return getDelegate().contains(o);
     }

     @Override
     public Iterator<E> iterator() {
          return new Iterator<E>() {
               
               private final Iterator<E> iteratorDelegate = AbstractObservableCollection.this.getDelegate().iterator();
               
               @Override
               public boolean hasNext() {
                    return iteratorDelegate.hasNext();
               }

               @Override
               public E next() {
                    return iteratorDelegate.next();
               }

              @Override
              public void remove() {
                  throw new UnsupportedOperationException();
              }
               
               
          };
     }

     @Override
     public Object[] toArray() {
          return getDelegate().toArray();
     }

     @Override
     public <T> T[] toArray(T[] a) {
          return getDelegate().toArray(a);
     }

     @Override
     public boolean add(E e){
         return isDelegateSizeChangedAfterRunning(
            ()  ->  fire(l->l.onAdd(createUnmodifiable(getDelegate()),e))
         );
     }

     @Override
     public boolean remove(Object o){
         return isDelegateSizeChangedAfterRunning(
            ()  ->  fire(l->l.onRemove(createUnmodifiable(getDelegate()),o))
         );
     }

     @Override
     public boolean containsAll(Collection<?> c) {
          return getDelegate().containsAll(c);
     }

     @Override
     public boolean addAll(Collection<? extends E> c) {
         return isDelegateSizeChangedAfterRunning(
            ()  ->  fire(l->l.onAddAll(createUnmodifiable(getDelegate()),c))
         );
     }

     @Override
     public boolean removeAll(Collection<?> c) {
         return isDelegateSizeChangedAfterRunning(
            ()  ->  fire(l->l.onRemoveAll(createUnmodifiable(getDelegate()),c))
         );
     }

     @Override
     public boolean retainAll(Collection<?> c) {
         return isDelegateSizeChangedAfterRunning(
            ()  ->  fire(l->l.onRetainAll(createUnmodifiable(getDelegate()),c))
         );
     }

     @Override
     public void clear() {
        fire(l->l.onClear(createUnmodifiable(getDelegate())));
     }
     
     
     
}
