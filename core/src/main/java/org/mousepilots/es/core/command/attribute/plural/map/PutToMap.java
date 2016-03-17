/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.map;

import java.util.List;
import java.util.Map;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 */
public class PutToMap<E, K,V> implements UpdateAttribute<E, Map<K,V>, MapAttributeESImpl<? super E,K,V>>{
     
     private transient List<Entry<Value<K,K,?,?>,Value<V,V,?,?>>> clientValues;

     @Override
     public void executeOnClient(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update){
          final Map<K, V> map = update.getAttribute().getJavaMember().get(update.getProxy().__subject());
          for(Entry<Value<K,K,?,?>,Value<V,V,?,?>> entry : clientValues){
               final K key = entry.getKey().getClientValue();
               final V newValue = entry.getValue().getClientValue();
               if(map.containsKey(key)){
               }
          }
     }

     @Override
     public void undo(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }

     @Override
     public void redo(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }

     @Override
     public void executeOnServer(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update, ServerContext serverContext) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
     
}
