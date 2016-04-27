/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.List;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.scenario.ScenarioException.Reason;

/**
 *
 * @author ap34wv
 */
public class DefaultCommandValidator{

    public void validate(List<Command> commands, ScenarioGraph graph, ServerContext context) throws IllegalCommandException{
        for(Command command : commands){
            final Vertex vertex = graph.getVertex(command.getType());
            if(vertex==null){
                throw new IllegalCommandException(command, Reason.TYPE_NOT_IN_GRAPH);
            }
            final CRUD operation = command.getOperation();
            switch(operation){
                //type level validator
                default : {
                    if(!vertex.isAllowedOnType(operation, context)){
                        throw new IllegalCommandException(command, Reason.OPERATION_NOT_ALLOWED_ON_TYPE);
                    }
                }
                
                case UPDATE : {
                    final Update update = (Update) command;
                    if(!vertex.isAllowedOnAttribute(update.getAttribute(), operation,context)){
                        throw new IllegalCommandException(command, Reason.OPERATION_NOT_ALLOWED_ON_ATTRIBUTE);
                    }
                }
            }
        }
    }
}
