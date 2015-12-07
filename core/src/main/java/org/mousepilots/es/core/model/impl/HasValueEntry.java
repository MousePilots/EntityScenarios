package org.mousepilots.es.core.model.impl;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.model.HasValue;

/**
 * Class that represents an entry in a {@link Map}.
 * @author Jurjen van Geenen
 * @version 1.0, 27-11-2015
 * @param <K> the type that represents the key.
 * @param <V> The type that represents the value.
 */
public class HasValueEntry<K ,V> implements Serializable{

     private HasValue<K> key;
     private HasValue<V> value;

     /**
      * Create a new instance of this class.
      */
     private HasValueEntry() {
     }

     /**
      * Create a new instance of this class.
      * @param key The key of a map.
      * @param value the value of a map.
      */
     public HasValueEntry(HasValue<K> key, HasValue<V> value) {
          this.key = key;
          this.value = value;
     }

     /**
      * Get the key for this entry.
      * @return the key.
      */
     public HasValue<K> getKey() {
          return key;
     }

     /**
      * Get the value for this entry.
      * @return the value.
      */
     public HasValue<V> getValue() {
          return value;
     }

    @Override
    public String toString() {
        return "HasValueEntry key: " + getKey().getValue() + ", value: "
                + getValue().getValue();
    }
}