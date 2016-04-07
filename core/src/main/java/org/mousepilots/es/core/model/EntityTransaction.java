/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import java.util.List;
import org.mousepilots.es.core.command.Command;

/**
 *
 * @author geenenju
 */
public interface EntityTransaction{

     boolean hasRedo();

     boolean hasUndo();
     
     List<Command> getCommands();

     /**
      * Redoes the effect of the last undone change
      * @throws IllegalStateException if {@code !hasRedo()}
      */
     void redo() throws IllegalStateException;


     /**
      * Undoes the effect of the last change in the undo/redo history
      * @throws IllegalStateException if {@code !hasRedo()}
      */
     void undo() throws IllegalStateException;
     
     /**
      * Undoes all changes
      */
     void rollback();
     
}
