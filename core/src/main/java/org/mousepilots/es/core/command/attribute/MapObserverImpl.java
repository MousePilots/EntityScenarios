/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.MapObserver;

/**
 * @author jgeenen
 * @param <E>
 * @param <K>
 * @param <V>
 */
public final class MapObserverImpl<E,K,V> extends PluralAttributeObserver<E,V,Map<K,V>,MapAttributeESImpl<? super E,K,V>> implements MapObserver<K,V>{

    private MapObserverImpl(){}

    public MapObserverImpl(Proxy<E> proxy, MapAttributeESImpl<? super E, K, V> attributeDescriptor) {
        super(proxy, attributeDescriptor);
    }
    
    @Override
    public void onClear(Map<K, V> subject) {
        if(!subject.isEmpty()){
            final RemoveFromMap<E, K, V> removeFromMap = new RemoveFromMap<>(getAttribute(),subject,subject.keySet());
            createAndExecute(removeFromMap);
        }
    }

    @Override
    public void onRemove(Map<K, V> subject, Object o) {
        final RemoveFromMap<E, K, V> removeFromMap = new RemoveFromMap<>(getAttribute(),subject,Collections.singleton((K) o));
        createAndExecute(removeFromMap);
    }

    @Override
    public void onPut(Map<K, V> subject, K key, V value) {
        final Map<K,V> putAlls = new HashMap<>();
        putAlls.put(key, value);
        this.onPutAll(subject, putAlls);
    }

    @Override
    public void onPutAll(Map<K, V> subject, Map<? extends K, ? extends V> putAlls) {
        final PutToMap<E,K,V> putToMap = new PutToMap<>(getAttribute(),subject,(Map<K, V>) putAlls);
        createAndExecute(putToMap);
        
    }
    
    
}
