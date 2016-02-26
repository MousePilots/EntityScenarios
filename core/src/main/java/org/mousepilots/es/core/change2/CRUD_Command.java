/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.TypeES;

/**
 *
 * @author geenenju
 */
public interface CRUD_Command extends Serializable{
     
     public void execute();
     
     public void undo();
     
     public void doOnServer(EntityManager entityManager, Map<TypeES,Map<Serializable,Serializable>> type2clientId2serverId);
     
     public void undoOnServer(EntityManager entityManager, Map<TypeES,Map<Serializable,Serializable>> type2clientId2serverId);
     
}
