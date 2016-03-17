/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author jgeenen
 * @param <E>
 */
public class ObservableCollection<E> extends AbstractObservableCollection<E, Collection<E>, CollectionObserver<E, Collection<E>>> implements Collection<E>{

    public ObservableCollection(){
        super(new ArrayList<>());
    }

    public ObservableCollection(Collection<E> delegate) {
        super(delegate);
    }

    @Override
    protected Collection<E> createUnmodifiable(Collection<E> delegate) {
        return Collections.unmodifiableCollection(delegate);
    }
    
    
    
}
