/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author jgeenen
 * @param <E>
 */
public class ObservableList<E> extends AbstractObservableCollection<E, List<E>, ListObserver<E>> implements List<E>{

    public ObservableList() {
        super(new ArrayList<>());
    }

    public ObservableList(List<E> delegate) {
        super(delegate);
    }

    
    @Override
    protected List<E> createUnmodifiable(List<E> delegate) {
        return Collections.unmodifiableList(delegate);
    }

    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
        this.fire(l->l.onAddAll(createUnmodifiable(this), index, c));
        return getDelegate().addAll(index, c);

    }

    @Override
    public E get(int index) {
        return getDelegate().get(index);
    }

    @Override
    public final E set(int index, E element){
        this.fire(l->l.onSet(createUnmodifiable(this), index, element));
        return getDelegate().set(index, element);
    }

    @Override
    public final void add(int index, E element) {
        this.fire(l->l.onSet(createUnmodifiable(this), index, element));
        getDelegate().add(index, element);

    }

    @Override
    public final E remove(int index) {
        this.fire(l->l.onRemove(createUnmodifiable(this), index));
        return getDelegate().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return getDelegate().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getDelegate().lastIndexOf(o);
    }

    /**
     * 
     * @return
     * @throws UnsupportedOperationException ALWAYS
     */
    @Override
    public ListIterator<E> listIterator() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return
     * @throws UnsupportedOperationException ALWAYS
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return
     * @throws UnsupportedOperationException ALWAYS
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GwtIncompatible @Override
    public void sort(Comparator<? super E> c) {
        this.getDelegate().sort(c);
    }
    
    
}
