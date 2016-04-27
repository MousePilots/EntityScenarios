/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import org.mousepilots.es.core.command.Command;

public final class IllegalCommandException extends ScenarioException{
    
    private Command command;

    private IllegalCommandException(){}

    public IllegalCommandException(Command command, Reason reason){
        super(reason);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
