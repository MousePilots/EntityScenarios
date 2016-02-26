/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <C>
 * @param <O>
 */
public abstract class AbstractObservableCollection<E,C extends Collection<E>,O extends CollectionObserver<E,C>> extends Observable<C,O> implements Collection<E>{

     protected AbstractObservableCollection(C delegate) {
          super(delegate);
     }

     @Override
     public int size() {
          return subject.size();
     }

     @Override
     public boolean isEmpty() {
          return subject.isEmpty();
     }

     @Override
     public boolean contains(Object o) {
          return subject.contains(o);
     }

     @Override
     public Iterator<E> iterator() {
          return new Iterator<E>() {
               
               private final Iterator<E> delegate = AbstractObservableCollection.this.subject.iterator();
               
               @Override
               public boolean hasNext() {
                    return delegate.hasNext();
               }

               @Override
               public E next() {
                    return delegate.next();
               }
          };
     }

     @Override
     public Object[] toArray() {
          return subject.toArray();
     }

     @Override
     public <T> T[] toArray(T[] a) {
          return subject.toArray(a);
     }

     @Override
     public boolean add(E e){
          fire(l->l.onAdd(createUnmodifiable(subject),e));
          return subject.add(e);
     }

     @Override
     public boolean remove(Object o){
          fire(l->l.onRemove(createUnmodifiable(subject),o));
          return subject.remove(o);
     }

     @Override
     public boolean containsAll(Collection<?> c) {
          return subject.containsAll(c);
     }

     @Override
     public boolean addAll(Collection<? extends E> c) {
          fire(l->l.onAddAll(createUnmodifiable(subject),c));
          return subject.addAll(c);
     }

     @Override
     public boolean removeAll(Collection<?> c) {
          fire(l->l.onRemoveAll(createUnmodifiable(subject),c));
          return subject.removeAll(c);

     }

     @Override
     public boolean retainAll(Collection<?> c) {
          fire(l->l.onRetainAll(createUnmodifiable(subject),c));
          return subject.retainAll(c);

     }

     @Override
     public void clear() {
          fire(l->l.onClear(createUnmodifiable(subject)));
          subject.clear();
     }
     
     
     
}
