/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

/**
 *
 * @author jgeenen
 */
public abstract class Element extends HasSeal{
    
    private final ScenarioGraph scenarioGraph;

    protected Element(ScenarioGraph getScenarioGraph) {
        this.scenarioGraph = getScenarioGraph;
    }

    public ScenarioGraph getScenarioGraph() {
        return scenarioGraph;
    }
    
}
