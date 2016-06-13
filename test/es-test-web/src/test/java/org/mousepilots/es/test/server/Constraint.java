/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author bhofsted
 */
@FunctionalInterface
public interface Constraint {
    
    void validate(List<Command> commands, ServerContext serverContext);
    
}
