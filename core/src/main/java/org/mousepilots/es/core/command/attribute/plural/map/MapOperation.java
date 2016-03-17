/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.map;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author geenenju
 */
public enum MapOperation {
     
     PUT(){

          @Override
          public <K, V> void executeOnServer(Map<K, V> map, ArrayList<K> keys, ArrayList<V> values) {
               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }


     },
     
     REMOVE(){

          @Override
          public <K, V> void executeOnServer(Map<K, V> map, ArrayList<K> keys, ArrayList<V> values) {
               for(K key : keys){
                    map.remove(key);
               }
          }
          
     };
     
     public abstract <K,V> void executeOnServer(Map<K,V> map, ArrayList<K> keys, ArrayList<V> values);
}
