/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author geenenju
 */
public class Updates {
  
     public static <X,Y> void onChange(Proxy<X> owner, SingularAttributeESImpl<X,Y> attribute, Y newValue){
          if(attribute.getType().getPersistenceType()==Type.PersistenceType.BASIC){
               
          } else {
               
          }
     }

     public static <X,E> void onChange(Proxy<X> owner, CollectionAttributeESImpl<X,E> attribute, Collection<E> newValue){
          
     }
     
     public static <X,E> void onChange(Proxy<X> owner, ListAttributeESImpl<X,E> attribute, List<E> newValue){
          
     }

     public static <X,E> void onChange(Proxy<X> owner, SetAttributeESImpl<X,E> attribute, Set<E> newValue){
          
     }

     public static <X,K,V> void onChange(Proxy<X> owner, MapAttributeESImpl<X,K,V> attribute, Map<K,V> newValue){
          
     }
     
}
