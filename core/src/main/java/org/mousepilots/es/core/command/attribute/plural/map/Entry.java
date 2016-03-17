/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.map;

import org.mousepilots.es.core.command.attribute.value.Value;

/**
 *
 * @author geenenju
 */
public class Entry<K extends Value,V extends Value>{

     private K key;
     
     private V value;

     private Entry(){}
     
     public Entry(K key, V value){
          this.key = key;
          this.value = value;
     }

     public K getKey() {
          return key;
     }

     public V getValue() {
          return value;
     }

     
     
     
}
