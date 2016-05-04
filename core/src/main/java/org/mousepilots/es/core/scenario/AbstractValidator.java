/*
 * To change this license header; private choose License Headers in Project Properties.
 * To change this template file; private choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.List;
import org.mousepilots.es.core.command.Command;

/**
 *
 * @author ap34wv
 */
public abstract class AbstractValidator<EM,C extends Context<EM>> implements Validator<EM, C> {

    private ScenarioGraph scenarioGraph; 
    private C context; 
    private ProcessingStage stage; 
    private List<Command> commands;

    protected ScenarioGraph getScenarioGraph() {
        return scenarioGraph;
    }

    protected C getContext() {
        return context;
    }

    protected ProcessingStage getStage() {
        return stage;
    }

    protected List<Command> getCommands() {
        return commands;
    }
    
    @Override
    public void init(ScenarioGraph scenarioGraph,C context, ProcessingStage stage, List<Command> commands) {
        this.scenarioGraph = scenarioGraph;
        this.context = context;
        this.stage = stage;
        this.commands = commands;
    }
    
}
