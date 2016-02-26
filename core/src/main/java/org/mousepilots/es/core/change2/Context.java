/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author geenenju
 */
public class Context {
     
     private final EntityManager entityManager; 
     private final Map<TypeES,Map<Serializable,Serializable>> type2clientId2serverId = new HashMap<>();

     public Context(EntityManager entityManager) {
          this.entityManager = entityManager;
     }

     public EntityManager getEntityManager() {
          return entityManager;
     }
     
     public <T extends Serializable> T getServerId(TypeES type, T clientId){
          return Maps.getOrDefault(type2clientId2serverId, clientId, type,clientId);
     }
     
     public void setServerId(TypeES type, Serializable clientId, Serializable serverId){
          final Map<Serializable, Serializable> clientId2serverId = Maps.getOrCreate(type2clientId2serverId, type, HashMap::new);
          final Serializable existing = clientId2serverId.put(clientId, serverId);
          if(existing!=null){
               throw new IllegalStateException("client Id " + clientId + " allready mapped to " + existing + " for type " + type);
          }
     }     
     
     
}
