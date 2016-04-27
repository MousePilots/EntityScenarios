/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Collection;



/**
 *
 * @author jgeenen
 * @param <EM> the entity-manager type
 */
public interface Context<EM> {
     /**
      * @return gets the user-name of the caller principal executing a scenario
      */
     String getUserName();

     /**
      * @param role
      * @return whether or not the caller principal has the specified {@code role}
      */
     boolean isUserInRole(String role);
     
     /**
      * Checks whether or not the caller principal has at least one of the specified {@code roles}
      * @param roles
      * @return whether or not {@link #isUserInRole(java.lang.String) } for some {@code role} &isin; {@code roles} 
      */
     default boolean hasRoleIn(Collection<String> roles){
         for(String role : roles){
             if(isUserInRole(role)){
                 return true;
             }
         }
         return false;
     }

     /**
      * @return the entity-manager for this context
      */
     EM getEntityManager();

}
