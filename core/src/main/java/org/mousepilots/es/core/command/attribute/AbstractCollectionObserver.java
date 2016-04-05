/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.CollectionObserver;

/**
 *
 * @author jgeenen
 */
public class AbstractCollectionObserver<E,EL,C extends Collection<EL>,AD extends PluralAttributeES<? super E,C,EL>> extends PluralAttributeObserver<E, EL, C, AD> implements CollectionObserver<EL, C>{

    protected AbstractCollectionObserver(){}

    protected AbstractCollectionObserver(Proxy<E> proxy, AD attributeDescriptor) {
        super(proxy, attributeDescriptor);
    }
    
    

    protected void createAndExecute(C subject, CollectionOperation collectionOperation, Collection<EL> elements){
        super.createandExecute(new UpdateCollection<>(getAttribute().getElementType(),CollectionOperation.ADD,subject,elements));
    }
    
    @Override
    public final void onAdd(C subject, EL e) {
        this.createAndExecute(subject, CollectionOperation.ADD, Collections.singleton(e));
    }

    @Override
    public final void onAddAll(C subject, Collection addAlls) {
        this.createAndExecute(subject, CollectionOperation.ADD, addAlls);
    }

    @Override
    public final void onRemoveAll(C subject, Collection<?> c) {
        this.createAndExecute(subject, CollectionOperation.REMOVE, (Collection) c);
    }

    @Override
    public final void onRetainAll(C subject, Collection<?> c) {
        Collection removals = new HashSet<>();
        for(Object element : c){
            if(subject.contains(element)){
                removals.add(element);
            }
        }
        this.createAndExecute(subject, CollectionOperation.REMOVE, removals);
    }

    @Override
    public final void onClear(C subject) {
        this.createAndExecute(subject, CollectionOperation.REMOVE, subject);
    }

    @Override
    public final void onRemove(C subject, Object o) {
        this.createAndExecute(subject, CollectionOperation.REMOVE, Collections.singleton((EL) o));
    }
    
    
    
}
