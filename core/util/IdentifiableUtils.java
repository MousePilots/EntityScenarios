/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.util;

import java.util.Collection;
import javax.persistence.Id;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.impl.IdentifiableTypeESImpl;

/**
 *
 * @author geenenju
 */
public class IdentifiableUtils {
     
     /**
      * Adds the {@link Id}-values of the {@code identifiables} to the supplied {@code ids} collection.
      * @param <T>
      * @param type
      * @param identifiables
      * @param ids 
      * @return the supplied {@link ids} collection for chaining 
      */
     public static <E,I> Collection<I> addIds(IdentifiableTypeESImpl<E> type, Collection<E> identifiables, Collection<I> ids){
          final MemberES idMember = (MemberES) type.getId().getJavaMember();
          for(Object identifiable : identifiables){
               ids.add(idMember.get(identifiable));
          }
          return ids;
     }
     
}
