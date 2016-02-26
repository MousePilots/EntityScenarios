/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

import java.util.Map;

/**
 *
 * @author geenenju
 */
public interface MapObserver<K,V> extends Observer<Map<K,V>>{
     
     void onPut(Map<K,V> subject, K key, V value);
     
     void onPutAll(Map<K,V> subject, Map<? extends K, ? extends V> putAlls);

}
