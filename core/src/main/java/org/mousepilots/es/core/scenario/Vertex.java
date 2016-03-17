/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.util.StringUtils;

/**
 * Represents a {@link ManagedTypeES} within a {@link ScenarioGraph}
 */
public class Vertex extends Element {
    
    private final ManagedTypeES type;
    
    //collections made unmodifiable by seal()
    private Set<Arc> in = new HashSet<>();
    private Set<Arc> out = new HashSet<>();
    private Set<Arc> touching = new HashSet<>();
    private Set<Vertex> predecessors = new HashSet<>();
    private Set<Vertex> successors = new HashSet<>();
    
    //unexposed fields
    private final Set<CRUD> allowedTypeLevelOperations = EnumSet.noneOf(CRUD.class);
    private final Map<AttributeES,EnumSet<CRUD>> allowedAttributeLevelOperations = new HashMap<>();
    private final String stringValue;
    
    protected Vertex(ScenarioGraph graph, ManagedTypeES managedType) {
        super(graph);
        this.type = managedType;
        stringValue = StringUtils.createToString(
            Vertex.class, 
            Arrays.asList(
                "scenario",graph.scenario,
                "type",type
            )
        );
    }
    
    protected void allow(CRUD... operations){
        assertNotSealed();
        allowedTypeLevelOperations.addAll(Arrays.asList(operations));
    }
    
    public boolean isAllowed(CRUD operation){
        return allowedTypeLevelOperations.contains(operation);
    }
    
    protected void allow(AttributeES attribute, CRUD... operations){
        assertNotSealed();
        final EnumSet<CRUD> allowedOperations = Maps.getOrCreate(
                allowedAttributeLevelOperations,
                attribute, 
                ()->{return EnumSet.noneOf(CRUD.class);}
        );
        allowedOperations.addAll(Arrays.asList(operations));
    }
    
    public boolean isAllowed(AttributeES attribute, CRUD operation){
        final EnumSet<CRUD> allowedOperations = allowedAttributeLevelOperations.get(attribute);
        return allowedOperations!=null && allowedOperations.contains(operation);
    }
    

    @Override
    public void seal() {
        in = Collections.unmodifiableSet(in);
        out = Collections.unmodifiableSet(out);
        touching = Collections.unmodifiableSet(touching);
        predecessors = Collections.unmodifiableSet(predecessors);
        successors = Collections.unmodifiableSet(successors);
    }
    
    public ManagedTypeES getType() {
        return type;
    }

    public Set<Arc> getIn() {
        return in;
    }

    public Set<Arc> getOut() {
        return out;
    }

    /**
     * @return the vertices which have outgoing arcs to {@code this}
     */
    public Set<Vertex> getPredecessors() {
        return predecessors;
    }

    /**
     * @return the vertices {@code this} has outgoing arcs to
     */
    public Set<Vertex> getSuccessors() {
        return successors;
    }

    /**
     * @return the {@link Arc}s touching {@code this}
     */
    public Set<Arc> getTouching() {
        return touching;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.stringValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return  stringValue;
    }
    
    
}
