/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model.impl;

import java.io.Serializable;
import org.mousepilots.es.model.HasValue;

/**
 *
 * @author geenenju
 */
public class HasValueEntry<K,V> implements Serializable{
     
     private HasValue<K> key;
     private HasValue<V> value;

     private HasValueEntry() {
     }

     public HasValueEntry(HasValue<K> key, HasValue<V> value) {
          this.key = key;
          this.value = value;
     }

     public HasValue<K> getKey() {
          return key;
     }

     public HasValue<V> getValue() {
          return value;
     }
     
}
