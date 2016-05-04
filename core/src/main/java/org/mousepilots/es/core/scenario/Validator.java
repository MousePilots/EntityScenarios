/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.List;
import org.mousepilots.es.core.command.Command;

/**
 *
 * @author ap34wv
 * @param <EM>
 * @param <C>
 */
public interface Validator<EM, C extends Context<EM>> {
    
    void init(ScenarioGraph scenarioGraph, C context, ProcessingStage stage, List<Command> commands);
    
    void validate() throws ScenarioException;
    
}
