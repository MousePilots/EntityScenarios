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
          fire(l->l.onAdd(createUnmodifiable(getDelegate()),e));
          return getDelegate().add(e);
     }

     @Override
     public boolean remove(Object o){
          fire(l->l.onRemove(createUnmodifiable(getDelegate()),o));
          return getDelegate().remove(o);
     }

     @Override
     public boolean containsAll(Collection<?> c) {
          return getDelegate().containsAll(c);
     }

     @Override
     public boolean addAll(Collection<? extends E> c) {
          fire(l->l.onAddAll(createUnmodifiable(getDelegate()),c));
          return getDelegate().addAll(c);
     }

     @Override
     public boolean removeAll(Collection<?> c) {
          fire(l->l.onRemoveAll(createUnmodifiable(getDelegate()),c));
          return getDelegate().removeAll(c);

     }

     @Override
     public boolean retainAll(Collection<?> c) {
          fire(l->l.onRetainAll(createUnmodifiable(getDelegate()),c));
          return getDelegate().retainAll(c);

     }

     @Override
     public void clear() {
          fire(l->l.onClear(createUnmodifiable(getDelegate())));
          getDelegate().clear();
     }
     
     
     
}
