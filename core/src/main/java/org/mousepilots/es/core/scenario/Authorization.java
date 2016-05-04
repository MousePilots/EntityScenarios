/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.Framework;
import org.mousepilots.es.core.util.StringUtils;

@Framework
final class Authorization implements Serializable{
    
    
    
    private final boolean ownershipRequired;
    
    private final String userName;
    
    private final Set<String> roles;
    
    private final Set<CRUD> operations;
    
    Authorization(boolean ownershipRequired, String userName, Set<String> roles, Set<CRUD> operations) {
        this.ownershipRequired = ownershipRequired;
        this.userName = userName;
        this.roles = Collections.unmodifiableSet(roles);
        this.operations = Collections.unmodifiableSet(operations);
    }

    /**
     * 
     * @param <T>
     * @param type
     * @param instance required <em>iff</em> {@code stage==}{@link ProcessingStage#AFTER}
     * @param operation
     * @param context
     * @param stage
     * @return 
     */
    <T> AuthorizationStatus getStatus(ManagedTypeES<? super T> type, T instance, CRUD operation, Context context, ProcessingStage stage) {
        if (!operations.contains(operation)) {
            return AuthorizationStatus.UNAUTHORIZED;
        }
        if (this.userName != null && !this.userName.equals(context.getUserName())) {
            return AuthorizationStatus.UNAUTHORIZED;
        }
        if(!this.roles.isEmpty() && !context.hasRoleIn(this.roles)){
            return AuthorizationStatus.UNAUTHORIZED;
        }
        switch(stage){
            case BEFORE : {
                return isGrantableBeforeProcessing() ? AuthorizationStatus.AUTHORIZED : AuthorizationStatus.REQUIRES_PROCESSING;
            }
            case AFTER: {
                if(isOwnershipRequired()){
                    final Set owners = type.getOwners(instance);
                    if(!owners.contains(context.getUserName())){
                        return AuthorizationStatus.UNAUTHORIZED;
                    }
                }
                return AuthorizationStatus.AUTHORIZED;
            }
            default: {
                throw new IllegalStateException("unknown stage " + stage);
            }
        }
    }
    
    /**
     * @return whether or not {@code this} requires ownership of the managed instance to which {@link #operations} apply
     */
    boolean isOwnershipRequired() {
        return ownershipRequired;
    }
    
    /**
     * @return the required user-name if any, otherwise {@code null}
     */
    String getUserName() {
        return userName;
    }

    /**
     * @return the roles, any of which is required, if any, otherwise an empty set
     */
    Set<String> getRoles() {
        return roles;
    }

    /**
     * @return 
     */
    Set<CRUD> getOperations() {
        return operations;
    }
    
    /**
     * @return whether or {@code this} is grantable before processing. E.g. checking ownership of a managed instance requires processing
     */
    boolean isGrantableBeforeProcessing(){
        return !isOwnershipRequired();
    }

    @Override
    public String toString() {
        return StringUtils.createToString(
            getClass(), Arrays.asList(
                "ownershipRequired", ownershipRequired,    
                "userName", userName,
                "roles", "[" + StringUtils.join(roles, r -> r, ",") + "]",
                "operations", "[" + StringUtils.join(operations, o -> o.toString(), ",") + "]"
            )
        );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.ownershipRequired ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.userName);
        hash = 37 * hash + Objects.hashCode(this.roles);
        hash = 37 * hash + Objects.hashCode(this.operations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Authorization other = (Authorization) obj;
        if (this.ownershipRequired != other.ownershipRequired) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        if (!Objects.equals(this.operations, other.operations)) {
            return false;
        }
        return true;
    }

    
    
}
