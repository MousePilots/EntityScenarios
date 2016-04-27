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
import java.util.List;
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

    /**
     * The possible operations at the attribute level are {@link CRUD#READ} and
     * {@link CRUD#UPDATE}
     */
    public static final Set<CRUD> ATTRIBUTE_LEVEL_OPERATIONS = Collections.unmodifiableSet(EnumSet.of(CRUD.READ, CRUD.UPDATE));

    private final ManagedTypeES type;

    //collections made unmodifiable by seal()
    private Set<Arc> in = new HashSet<>();
    private Set<Arc> out = new HashSet<>();
    private Set<Arc> touching = new HashSet<>();
    private Set<Vertex> predecessors = new HashSet<>();
    private Set<Vertex> successors = new HashSet<>();

    //unexposed fields
    private final Set<Authorization> typeAuthorizations = new HashSet<>();
    private final Map<AttributeES, Set<Authorization>> attributeAuthroizations = new HashMap<>();
    private final String stringValue;

    private void assertAttributeOccurs(AttributeES attribute) throws IllegalArgumentException {
        if (!getType().getAttributes().contains(attribute)) {
            throw new IllegalArgumentException(attribute + " does not occur on " + getType());
        }
    }

    protected Vertex(ScenarioGraph graph, ManagedTypeES managedType) {
        super(graph);
        this.type = managedType;
        stringValue = StringUtils.createToString(Vertex.class,
                Arrays.asList(
                        "scenario", graph.scenario,
                        "type", type
                )
        );
    }

    protected void addTypeLevelAuthorization(Authorization authorization) {
        assertNotSealed();
        if(!typeAuthorizations.add(authorization)){
            throw new IllegalStateException("duplicate authorization for " + getType() + ": " + authorization);
        }
    }
    
    protected void addAttributeLevelAuthorization(AttributeES attribute, Authorization authorization){
        assertNotSealed();
        assertAttributeOccurs(attribute);
        final Set<CRUD> operations = authorization.getOperations();
        if (!ATTRIBUTE_LEVEL_OPERATIONS.containsAll(operations)) {
            throw new IllegalArgumentException(
                    StringUtils.join(operations, o -> o.name(), ", ")
                    + " contains at least one illegal operation specified for " + attribute + ". Allowed are "
                    + StringUtils.join(ATTRIBUTE_LEVEL_OPERATIONS, o -> o.name(), ", ") + " are allowed"
            );
        }
        final Set<Authorization> authorizations = Maps.getOrCreate(attributeAuthroizations, attribute, HashSet::new);
        if(!authorizations.add(authorization)){
            throw new IllegalStateException("duplicate authorization for " + attribute + ": " + authorization);
        }
    }

    /**
     * @param operation
     * @param context
     * @return whether or not the {@code operation} is allowed on {@link #getType()} within the specified {@code context} 
     */
    public boolean isAllowedOnType(CRUD operation, Context context) {
        for(Authorization typeAuthorization : typeAuthorizations){
            if(typeAuthorization.isGranted(context, operation)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param attributeES
     * @param operation
     * @param context
     * @return whether or not the {@code operation} is allowed on the {@link attribute} within the specified {@code context}
     */
    public boolean isAllowedOnAttribute(AttributeES attributeES, CRUD operation, Context context){
        assertAttributeOccurs(attributeES);
        final Set<Authorization> authorizations = this.attributeAuthroizations.get(attributeES);
        if(authorizations!=null){
            for(Authorization authorization : authorizations){
                if(authorization.isGranted(context, operation)){
                    return true;
                }
            }
        } 
        return false;
    }

    @Override
    public void seal() {
        if (!isSealed()) {
            in = Collections.unmodifiableSet(in);
            out = Collections.unmodifiableSet(out);
            touching = Collections.unmodifiableSet(touching);
            predecessors = Collections.unmodifiableSet(predecessors);
            successors = Collections.unmodifiableSet(successors);
            super.seal();
        }
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
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
