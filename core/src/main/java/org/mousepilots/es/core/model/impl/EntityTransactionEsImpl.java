/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityTransactionES;
import org.mousepilots.es.core.util.StringUtils;

/**
 *
 * @author geenenju
 */
public class EntityTransactionEsImpl implements EntityTransactionES {

     private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
     
     private final ArrayList<Command> commands = new ArrayList<>();
     
     private final long id = ID_GENERATOR.getAndIncrement();

     private int insertionIndex = 0;

     @Override
     public void begin(){}

     @Override
     public void commit(){
          if(hasRedo()){
               final List<Command> redoables = commands.subList(insertionIndex,commands.size());
               redoables.clear();
          }
     }

     @Override
     public void rollback() {
          while(hasUndo()){
               undo();
          }
     }
     
     /**
      * Associates the command with {@code this} transaction. 
      * Must be invoked exactly once, immedeately after the first invocation of {@link Command#executeOnClient()}
      * @param command 
      */
     void associate(Command command){
          if(commands.size()>insertionIndex){
               //discard redoable changes from insertion index onwards
               commands.subList(insertionIndex, commands.size()).clear();
          }
          commands.add(command);
          insertionIndex++;
     }

     @Override
     public boolean hasUndo() {
          return 0 < insertionIndex;
     }
     
     /**
      * Undoes the effect of the previous command in the undoOnClient/redoOnClient history
      * @throws IllegalStateException 
      */
     @Override
     public void undo() throws IllegalStateException{
          if(hasUndo()) {
               final Command command = commands.get(--insertionIndex);
               command.undoOnClient();
          } else {
               throw new IllegalStateException("nothing to be undone");
          }
     }

     @Override
     public boolean hasRedo() {
          return insertionIndex<commands.size();
     }

     @Override
     public void redo() throws IllegalStateException{
          if(hasRedo()){
               final Command command = commands.get(insertionIndex++);
               command.executeOnClient();
          } else {
               throw new IllegalStateException("nothing to be redone");
          }
     }

     /**
      * 
      * @throws UnsupportedOperationException ALWAYS
      */
     @Override
     public void setRollbackOnly() throws UnsupportedOperationException{
          throw new UnsupportedOperationException("Not supported yet.");
     }

     /**
      * @return
      * @throws UnsupportedOperationException ALWAYS
      */
     @Override
     public boolean getRollbackOnly() throws UnsupportedOperationException {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     /**
      * 
      * @return
      * @throws UnsupportedOperationException ALWAYS
      */
     @Override
     public boolean isActive() throws UnsupportedOperationException{
          throw new UnsupportedOperationException("Not supported yet.");
     }

     @Override
     public int hashCode() {
          int hash = 5;
          hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
          return hash;
     }

     @Override
     public boolean equals(Object obj) {
          if (obj == null) {
               return false;
          }
          if (getClass() != obj.getClass()) {
               return false;
          }
          final EntityTransactionEsImpl other = (EntityTransactionEsImpl) obj;
          if (this.id != other.id) {
               return false;
          }
          return true;
     }

     @Override
     public String toString() {
          return StringUtils.createToString(getClass(), Arrays.asList("id",id));
     }
     
     

}
