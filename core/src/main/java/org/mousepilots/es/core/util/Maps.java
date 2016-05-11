/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import org.mousepilots.es.core.model.impl.Constructor;


/**
 * @author geenenju
 */
public class Maps
{
   private Maps() {
   }


   public static final Constructor<ArrayList>   ARRAY_LIST_CREATOR = ArrayList::new;
   public static final Constructor<TreeSet>     TREE_SET_CREATOR = TreeSet::new;
   public static final Constructor<HashSet>     HASH_SET_CREATOR = HashSet::new;
   public static final Constructor<HashMap>     HASH_MAP_CREATOR = HashMap::new;

   public static <K extends Enum<K>, V> Map<K, V> create(Class<K> enumClass, List<K> keys, List<V> values)
   {
      if (keys.size() != values.size())
      {
         throw new IllegalArgumentException("keys and values have unqueal sizes");
      }
      else
      {
         Map<K, V> retval = new EnumMap<>(enumClass);
         for (int i = 0; i < keys.size(); i++)
         {
            retval.put(keys.get(i), values.get(i));
         }
         return retval;
      }
   }

   public static <K, V> Map<K, V> create(List<K> keys, List<V> values)
   {
      if (keys.size() != values.size())
      {
         throw new IllegalArgumentException("keys and values have unqueal sizes");
      }
      else
      {
         Map<K, V> retval = new HashMap<>();
         for (int i = 0; i < keys.size(); i++)
         {
            retval.put(keys.get(i), values.get(i));
         }
         return retval;
      }
   }

   public static <K, V> Map<V, K> invert(Map<K, V> bijection)
   {
      HashMap<V, K> retval = new HashMap<>();
      for (Entry<K, V> e : bijection.entrySet())
      {
         final K existing = retval.put(e.getValue(), e.getKey());
         if (existing != null)
         {
            throw new IllegalArgumentException(bijection + " is no bijection: duplicate values found for keys " + existing + " and " + e.getKey() + ":" + e.getValue());
         }
      }
      return retval;
   }

   public static <K, V,C extends Collection<? extends V>> Map<K, V> create(C values, Function<V, K> f)
   {
      final Map<K, V> retval = new HashMap<>();
      for (V value : values)
      {
         final K key = f.apply(value);
         final V existingValue = retval.put(key, value);
         if (existingValue != null)
         {
            throw new IllegalArgumentException("duplicate mapping for key " + key + ": " + existingValue + ", " + value);
         }
      }
      return retval;
   }
   
   public static <E,K,V> Map<K,V> create(Collection<E> elements, Function<E,K> keyGen, Function<E,V> valueGen){
      final Map<K, V> retval = new HashMap<>();
      for(E element : elements){
          final V put = retval.put(keyGen.apply(element), valueGen.apply(element));
          if(put!=null){
              throw new IllegalArgumentException("duplicate key encountered for element " + element);
          }
      }
      return retval;
   }

   public static <K, V, M extends Map<K, V>> V getOrCreate(M map, K key, Constructor<V> constructor)
   {
      V retval = map.get(key);
      if (retval == null)
      {
         retval = (V) constructor.invoke();
         map.put(key, retval);
      }
      return retval;
   }

   public static <V> V get(Map nestedMap, Object... keys)
   {
      Map currentMap = nestedMap;
      for (int i = 0; currentMap!=null && i < keys.length; i++)
      {
         Object value = currentMap.get(keys[i]);
         if (i == keys.length - 1 || value == null)
         {
            return (V) value;
         }
         else
         {
            currentMap = (Map) value;
         }
      }
      return null;
   }
   
   public static <V> V getOrDefault(Map nestedMap, V defaultValue, Object... keys)
   {
        final V retval = get(nestedMap, keys);
        return retval==null ? defaultValue : retval;
   }
   

}
