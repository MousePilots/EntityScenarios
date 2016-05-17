/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.priviliges;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.core.util.Maps;

/**
 * Contains the privileges for a specific scenario and {@link Context}. Instances should be considered request-scoped, as they are scenario and context specific. Instances are thread-safe after construction.
 * @author jgeenen
 */
public class Priviliges {
    
    private final Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> privileges;

    /**
     * @param privileges mapping from operation &rarr; managed type on which the operation is allowed &rarr attributes of the managed type on which the operation is allowed. The set of attributes is undefined for {@link CRUD#DELETE}s
     */
    public Priviliges(Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> privileges) {
        this.privileges = privileges;
    }
    
    /**
     * Query whether or not the {@code operation} is allowed on the {@code type}, {@code attribute combination}. {@code attribute}
     * is silently ignored for {@link CRUD#DELETE}s.
     * 
     * @param operation
     * @param type
     * @param attribute required <em>if and only if</em> {@code operation!=}{@link CRUD#DELETE}
     * @return 
     */
    public boolean isAllowed(CRUD operation, ManagedTypeES type, AttributeES attribute){
        final Map<ManagedTypeES, Set<AttributeES>> type2attributes = privileges.get(operation);
        if(type2attributes!=null && type2attributes.containsKey(type)){
            if(operation==CRUD.DELETE){
                //no need to inspect attribute
                return true;
            } else {
                //check if attribute is allowed
                Objects.requireNonNull(attribute, "attribute is required for operation " + operation + " on " + type);
                final Set<AttributeES> attributeLevelPriviliges = type2attributes.get(type);
                return attributeLevelPriviliges!=null && attributeLevelPriviliges.contains(attribute);
            }
        } else {
            return false;
        }
    }
    
    /**
     * @param command
     * @return whether or not the {@code command} is allowed according to {@code this}
     */
    public boolean isAllowed(Command command){
        final ManagedTypeES type = command.getType();
        final CRUD operation;
        final AttributeES attribute;
        if(command instanceof Update){
            final Update update = ((Update)command);
            operation = update.getCreateCommand()==null ? CRUD.UPDATE : CRUD.CREATE;
            attribute = update.getAttribute();
        } else {
            operation = command.getOperation();
            attribute = null;
        }
        return isAllowed(operation, type, attribute);
    }

    /**
     * An unmodifiable non-null set of managed types on which the {@code operation} is allowed
     * @param operation
     * @return an unmodifiable non-null set of managed types on which the {@code operation} is allowed
     */
    public Set<ManagedTypeES> getTypes(CRUD operation){
        final Set<ManagedTypeES> types = Maps.get(privileges, operation);
        if(types==null){
            return Collections.EMPTY_SET;
        } else {
            return Collections.unmodifiableSet(types);
        }
    }
    
    /**
     * An unmodifiable non-null set of attributes for which the {@code operation} is allowed on the specified {@link managedType}
     * @param operation
     * @param managedType
     * @return an unmodifiable non-null set of attributes for which the {@code operation} is allowed on the specified {@link managedType}
     * @throws IllegalArgumentException if {@code operation==}{@link CRUD#DELETE}
     */
    public Set<AttributeES> getAttributes(CRUD operation, ManagedTypeES managedType) throws IllegalArgumentException{
        if(operation==CRUD.DELETE){
            throw new IllegalArgumentException("DELETEs do not operate at the attribute level");
        }
        final Set<AttributeES> attributes = Maps.get(privileges, operation,managedType);
        if(attributes==null){
            return Collections.EMPTY_SET;
        } else {
            return Collections.unmodifiableSet(attributes);
        }
    }
    
    
}
