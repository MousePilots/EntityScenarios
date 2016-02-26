/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.constraint;

import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.core.scenario.ScenarioGraph;

/**
 *
 * @author jgeenen
 */
public abstract class ReadConstraint<O,T> {
    
    private final O owner;

    public ReadConstraint(O owner) {
        this.owner = owner;
    }

    public O getOwner() {
        return owner;
    }
    
    public abstract boolean isSatisfied(ScenarioGraph scenarioGraph, Context context, T value);
}
