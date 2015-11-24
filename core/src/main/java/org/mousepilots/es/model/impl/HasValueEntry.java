package org.mousepilots.es.model.impl;

import java.io.Serializable;
import org.mousepilots.es.model.HasValue;

/**
 * @author Jurjen van Geenen
 * @version 1.0, 20-11-2015
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