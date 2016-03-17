/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import javax.persistence.EntityTransaction;

/**
 *
 * @author geenenju
 */
public interface EntityTransactionES extends EntityTransaction{

     /**
      * No-op.
      */
     @Override
     public default void begin(){}

     /**
      * Not supported yet.
      * @throws UnsupportedOperationException ALWAYS
      */
     @Override
     public default void setRollbackOnly() throws UnsupportedOperationException{
          throw new UnsupportedOperationException("Not supported yet.");
     }

     boolean hasRedo();

     boolean hasUndo();

     void redo() throws IllegalStateException;

     void rollback();

     /**
      * Undoes the effect of the previous command in the undo/redo history
      * @throws IllegalStateException
      */
     void undo() throws IllegalStateException;
     
     
     
}
