/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jgeenen
 */
public class ObservableSet<E> extends AbstractObservableCollection<E, Set<E>, SetObserver<E>> implements Set<E>{

    public ObservableSet(){
        super(new HashSet<>());
    }

    public ObservableSet(Set<E> delegate) {
        super(delegate);
    }

    @Override
    protected Set<E> createUnmodifiable(Set<E> delegate) {
        return Collections.unmodifiableSet(delegate);
    }

}
