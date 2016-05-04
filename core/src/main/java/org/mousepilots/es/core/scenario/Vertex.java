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
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasAttribute;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.util.StringUtils;

/**
 * Represents a {@link ManagedTypeES} within a {@link ScenarioGraph}
 */
public final class Vertex extends Element {

    /**
     * The possible operations at the attribute level are {@link CRUD#READ} and
     * {@link CRUD#UPDATE}
     */
    public static final Set<CRUD> ATTRIBUTE_LEVEL_OPERATIONS = Collections.unmodifiableSet(EnumSet.of(CRUD.READ, CRUD.UPDATE));
    
    private static AuthorizationStatus getAuthorizationStatus(
            Set<Authorization> authorizations, 
            Command command, 
            Context context, 
            ProcessingStage stage){
        AuthorizationStatus returnedStatus = AuthorizationStatus.UNAUTHORIZED;
        if(authorizations!=null){
            for(Authorization typeAuthorization : authorizations){
                final AuthorizationStatus typeAuthorizationStatus = typeAuthorization.getStatus(
                    command.getType(),
                    command.getRealSubject(),
                    command.getOperation(),
                    context,
                    stage
                );
                switch(typeAuthorizationStatus){
                    case AUTHORIZED : {
                        return AuthorizationStatus.AUTHORIZED;
                    }
                    case REQUIRES_PROCESSING : {
                        returnedStatus = AuthorizationStatus.REQUIRES_PROCESSING;
                    }
                }
            }
        }
        return returnedStatus;
    }      

    private final ManagedTypeES type;

    //collections made unmodifiable by seal()
    private Set<Arc> in = new HashSet<>();
    private Set<Arc> out = new HashSet<>();
    private Set<Arc> touching = new HashSet<>();
    private Set<Vertex> predecessors = new HashSet<>();
    private Set<Vertex> successors = new HashSet<>();

    //unexposed fields
    private final Set<Authorization> typeAuthorizations = new HashSet<>();
    private final Map<AttributeES, Set<Authorization>> attributeAuthorizations = new HashMap<>();
    private final String stringValue;

    private void assertAttributeOccurs(AttributeES attribute) throws IllegalArgumentException {
        if (!getType().getAttributes().contains(attribute)) {
            throw new IllegalArgumentException(attribute + " does not occur on " + getType());
        }
    }
    
    private static Authorization[] getFirstAmbiguousPair(Set<Authorization> authorizations){
        final Authorization[] authArray = authorizations.toArray(new Authorization[authorizations.size()]);
        for(int i=0; i<authArray.length-1; i++){
            for(int j=i+1; j<authArray.length; i++){
                final Authorization ai = authArray[i], aj= authArray[j];
                final boolean ambiguous = 
                        !Collections.disjoint(ai.getOperations(), aj.getOperations()) &&
                        !Collections.disjoint(ai.getRoles(), aj.getRoles()) &&
                        Objects.equals(ai.getUserName(), aj.getUserName());
                if(ambiguous){
                    return new Authorization[]{ai,aj};
                }
            }
        }
        return null;
    }
    
    void assertTypeAuthorizationsAreNotAmbiguous(){
        final Authorization[] ambiguousTypeAuthorizations = getFirstAmbiguousPair(typeAuthorizations);
        if(ambiguousTypeAuthorizations!=null){
            throw new IllegalStateException(
                    "authorizations " + ambiguousTypeAuthorizations[0] + ", " + 
                    ambiguousTypeAuthorizations[1] + " for " + this + " are ambiguous"
            );
        }
    }
    
    void assertAttributeAuthorizationsAreNotAmbiguous(AttributeES attribute){
        final Set<Authorization> authorizations = attributeAuthorizations.get(attribute);
        if(authorizations!=null){
            final Authorization[] ambiguousAttributeAuthorizations = getFirstAmbiguousPair(authorizations);
            if(ambiguousAttributeAuthorizations!=null){
                throw new IllegalStateException(
                        "authorizations " + ambiguousAttributeAuthorizations[0] + ", " + 
                        ambiguousAttributeAuthorizations[1] + " for attribute " + attribute + " on " + this + " are ambiguous"
                );
            }
        }
    }
    

    Vertex(ScenarioGraph graph, ManagedTypeES managedType) {
        super(graph);
        this.type = managedType;
        stringValue = StringUtils.createToString(Vertex.class,
            Arrays.asList(
                "scenario", graph.scenario,
                "type", type
            )
        );
    }

    void addTypeLevelAuthorization(Authorization authorization) {
        assertNotSealed();
        if(!typeAuthorizations.add(authorization)){
            throw new IllegalStateException("duplicate authorization for " + getType() + ": " + authorization);
        }
    }
    
    void addAttributeLevelAuthorization(AttributeES attribute, Authorization authorization){
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
        final Set<Authorization> authorizations = Maps.getOrCreate(attributeAuthorizations, attribute, HashSet::new);
        if(!authorizations.add(authorization)){
            throw new IllegalStateException("duplicate authorization for " + attribute + ": " + authorization);
        }
    }

    private AuthorizationStatus getTypeAuthorizationStatus(Command command, Context context, ProcessingStage stage) {
        return Vertex.getAuthorizationStatus(typeAuthorizations, command, context, stage);
    }

    private AuthorizationStatus getAttributeAuthorizationStatus(Command command, AttributeES attribute, Context context, ProcessingStage stage) {
        return Vertex.getAuthorizationStatus(attributeAuthorizations.get(attribute), command, context, stage);
    }
    
    public AuthorizationStatus getAuthorizationStatus(Command command, Context context, ProcessingStage stage){
        final AuthorizationStatus typeLevelStatus = this.getTypeAuthorizationStatus(command, context, stage);
        if(typeLevelStatus!=AuthorizationStatus.UNAUTHORIZED && command instanceof HasAttribute){
            final HasAttribute hasAttribute = (HasAttribute) command;
            final AttributeES attribute = hasAttribute.getAttribute();
            final AuthorizationStatus attributeLevelStatus = this.getAttributeAuthorizationStatus(command, attribute, context, stage);
            return attributeLevelStatus.least(typeLevelStatus);
        } else {
            return typeLevelStatus;
        }
    }

    protected boolean isReadable(Context context){
        for(Authorization authorization : typeAuthorizations){
            if(authorization.getStatus(type, null, CRUD.READ, context, ProcessingStage.BEFORE)==AuthorizationStatus.AUTHORIZED){
                return true;
            }
        }
        return false;
    }
    
    public Set<AttributeES> getReadableAttributes(Context context){
        if(isReadable(context)){
            final Set<AttributeES> retval = new HashSet<>();
            for( Map.Entry<AttributeES, Set<Authorization>> entry : this.attributeAuthorizations.entrySet() ) {
                for(Authorization authorization : entry.getValue()){
                    if(authorization.getStatus(type, null, CRUD.READ, context, ProcessingStage.BEFORE)==AuthorizationStatus.AUTHORIZED){
                        retval.add(entry.getKey());
                        break;
                    }
                }
            }
            return retval;
        } else {
            return Collections.EMPTY_SET;
        }
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
