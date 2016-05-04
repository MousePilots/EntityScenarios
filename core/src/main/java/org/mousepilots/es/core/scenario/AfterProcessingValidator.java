/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;

/**
 *
 * @author jgeenen
 */
public class AfterProcessingValidator<EM,C extends Context<EM>> extends AbstractValidator<EM, C>{
    
    @Override
    public void validate() throws ScenarioException {
        final ScenarioGraph scenarioGraph = getScenarioGraph();
        final C context = getContext();
        final ProcessingStage stage = getStage();
        for(Command command : getCommands()){
            final ManagedTypeES type = command.getType();
            final Vertex vertex = scenarioGraph.getVertex(type);
            if(vertex==null){
                throw new IllegalCommandException(
                    command, 
                    ScenarioException.Reason.TYPE_NOT_IN_GRAPH
                );
            }
            final AuthorizationStatus status = vertex.getAuthorizationStatus(command, context, stage);
            if(status!=AuthorizationStatus.AUTHORIZED){
                throw new IllegalCommandException(
                    command, 
                    ScenarioException.Reason.COMMAND_NOT_ALLOWED
                );
            }
        }
    }
    
}
