/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityTransaction;

/**
 *
 * @author geenenju
 */
public class EntityTransactionImpl implements EntityTransaction {

    private final List<Command> commands = new ArrayList<>();

    private int commandInsertionIndex = 0;

    @Override
    public void rollback() {
        while (hasUndo()) {
            undo();
        }
    }

    public final void associate(Command command) throws IllegalStateException{
        if (commands.contains(command)) {
            throw new IllegalStateException(command + " is allready associated to " + this);
        }
        if (commands.size() > commandInsertionIndex) {
            //discard redoable changes from insertion index onwards
            commands.subList(commandInsertionIndex, commands.size()).clear();
        }
        commands.add(command);
        commandInsertionIndex++;
    }

    @Override
    public boolean hasUndo() {
        return 0 < commandInsertionIndex;
    }

    /**
     * Undoes the effect of the previous command in the
     * undoOnClient/redoOnClient history
     *
     * @throws IllegalStateException if {@code !this.hasUndo()}
     */
    @Override
    public void undo() throws IllegalStateException {
        if (hasUndo()) {
            final Command command = commands.get(--commandInsertionIndex);
            command.undoOnClient();
        } else {
            throw new IllegalStateException("nothing to undo");
        }
    }

    @Override
    public boolean hasRedo() {
        return commandInsertionIndex < commands.size();
    }

    @Override
    public void redo() throws IllegalStateException {
        if (hasRedo()) {
            final Command command = commands.get(commandInsertionIndex++);
            command.executeOnClient();
        } else {
            throw new IllegalStateException("nothing to be redone");
        }
    }

    @Override
    public List<Command> getCommands() {
        return new ArrayList(this.commands.subList(0, commandInsertionIndex));
    }
    
    

}
