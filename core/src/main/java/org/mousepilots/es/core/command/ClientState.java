/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

/**
 * Enumerates the possible states of a {@link Command} on the client which created the command
 * @author geenenju
 */
public enum ClientState {
     CREATED {
          @Override
          public ClientState getNext() {
               return EXECUTED;
          }
     }, EXECUTED {
          @Override
          public ClientState getNext() {
               return UNDONE;
          }
     }, UNDONE {
          @Override
          public ClientState getNext() {
               return REDONE;
          }
     }, REDONE {
          @Override
          public ClientState getNext() {
               return UNDONE;
          }
     };
     /**
      * @return the one and only possible next state after {@code this}
      */
     public abstract ClientState getNext();
     
     public final void assertValidNextState(ClientState next) throws IllegalStateException{
         final ClientState expected = getNext();
         if(expected!=next){
             throw new IllegalStateException("expected: " + expected + ", encountered: " + next);
         }
     }
     
}
