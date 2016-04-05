/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.ListObserver;

/**
 *
 * @author jgeenen
 * @param <E>
 * @param <EL>
 */
public class ListObserverImpl<E, EL> extends AbstractCollectionObserver<E, EL, List<EL>, ListAttributeES<? super E, EL>> implements ListObserver<EL> {

    public ListObserverImpl() {
    }

    public ListObserverImpl(Proxy<E> proxy, ListAttributeES<? super E, EL> attributeDescriptor) {
        super(proxy, attributeDescriptor);
    }

    @Override
    public void onSet(List<EL> subject, int index, EL element) {
        final EL old = subject.get(index);
        if (!Objects.equals(old, element)) {
            createAndExecute(subject, CollectionOperation.REMOVE, Collections.singleton(old));
            createAndExecute(subject, CollectionOperation.ADD, Collections.singleton(element));
        }
    }

    @Override
    public void onAdd(List<EL> subject, int index, EL element) {
        final List<EL> removals = subject.subList(index, subject.size());
        final List<EL> additions = new ArrayList<>(removals.size()+1);
        additions.add(element);
        additions.addAll(removals);
        createAndExecute(subject, CollectionOperation.REMOVE, removals);
        createAndExecute(subject, CollectionOperation.ADD, additions);

    }

    @Override
    public void onAddAll(List<EL> subject, int index, Collection<? extends EL> a) {
        final List<EL> removals = subject.subList(index, subject.size());
        final List<EL> additions = new ArrayList<>(removals.size() + a.size());
        additions.addAll(a);
        additions.addAll(removals);
        createAndExecute(subject, CollectionOperation.REMOVE, removals);
        createAndExecute(subject, CollectionOperation.ADD, additions);

    }

    @Override
    public void onRemove(List<EL> subject, int index) {
        createAndExecute(subject, CollectionOperation.REMOVE, Collections.singleton(subject.get(index)));
    }

}
