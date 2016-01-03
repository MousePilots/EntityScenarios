/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Arrays;
import java.util.Objects;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.util.StringUtils;

/**
 *
 * @author jgeenen
 */
public class Arc extends Element {
    
    final AssociationES association;
    final Vertex source;
    private final Vertex target;
    private final String stringValue;

    protected Arc(ScenarioGraph scenarioGraph, AssociationES association, Vertex source, Vertex target) {
        super(scenarioGraph);
        this.association = association;
        this.source = source;
        this.target = target;
        stringValue = StringUtils.createToString(
            Arc.class, 
            Arrays.asList(
                "scenario",     scenarioGraph.scenario, 
                "association",  association.toString()
            )
        );
    }

    public AssociationES getAssociation() {
        return association;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.stringValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public String toString() {
        return stringValue;
    }
    
    
    
}
