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

     public abstract ClientState getNext();
     
}
