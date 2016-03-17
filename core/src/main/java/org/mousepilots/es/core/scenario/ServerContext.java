/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.IdentifiableTypeES;


/**
 *
 * @author geenenju
 */
public interface ServerContext extends Context<EntityManager>{
     
     <I,T> I getServerId(IdentifiableTypeES<? super T> type, I clientId);
     
     /**
      * Executed after execution on server of the specified {@code command}
      * @param command 
      */
     void onExecuteOnServer(Command command);
     
     /**
      * Finds the entity of the given {@code type}, with the specified {@code clientId}
      * @param <E>
      * @param <ID>
      * @param type
      * @param clientId
      * @return the entity
      */
     default <E,ID> E find(IdentifiableTypeES<E> type, ID clientId){
          final ID serverId = getServerId(type, clientId);
          return getEntityManager().find(type.getJavaType(), serverId);
     }
}
