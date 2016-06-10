/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import com.google.gwt.core.shared.GwtIncompatible;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author bhofsted
 */

public abstract class Request {
    
    private transient ServerContext serverContext;
    
    private List<Command> commands;

    public abstract String getScenario();

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    
    @GwtIncompatible
    public ServerContext getServerContext() {
        return serverContext;
    
    }
    @GwtIncompatible
    public void setServerContext(ServerContext serverContext) {
        this.serverContext = serverContext;
    }
    
    //public abstract void accept(V visitor);
}
