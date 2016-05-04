/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;

/**
 *
 * @author ap34wv
 * @param <EM>
 * @param <C>
 */
public class BeforeProcessingValidator<EM,C extends Context<EM>> extends AbstractValidator<EM, C>{
    
    private final List<Command> toBeValidatedAfterProcessing = new LinkedList<>();

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
            switch(vertex.getAuthorizationStatus(command, context, stage)){
                case AUTHORIZED : break;
                case REQUIRES_PROCESSING : toBeValidatedAfterProcessing.add(command); break;
                case UNAUTHORIZED : throw new IllegalCommandException(command, ScenarioException.Reason.COMMAND_NOT_ALLOWED);
            }
        }
    }

    /**
     * @return the {@link Command}s to be validated at {@link ProcessingStage#AFTER}
     */
    public List<Command> getToBeValidatedAfterProcessing() {
        return Collections.unmodifiableList(toBeValidatedAfterProcessing);
    }
}
