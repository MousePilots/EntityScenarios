/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

/**
 *
 * @author geenenju
 */
public interface ClientStateTransitionListener {
     
     /**
      * Invoked before {@code command}'s transition to {@code nextState}
      * @param command
      * @param nextState 
      */
     void beforeStateChange(Command command, ClientState nextState);

     /**
      * Invoked after {@code command}'s state change from {@code previousState}
      * @param command
      * @param previousState 
      */
     void afterStateChange(Command command, ClientState previousState);
}
