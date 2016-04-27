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
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.util.Framework;
import org.mousepilots.es.core.util.StringUtils;

@Framework
final class Authorization implements Serializable{
    
    static enum Status {
        AUTHORIZED,
        UNAUTHORIZED,
        BEFORE_COMMIT_VALIDATION_REQUIRED;
    }
    
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

    private boolean satisfiesBeforeProcessingProcessingConstraints(CRUD operation, Context context) {
        if (!operations.contains(operation)) {
            return false;
        }
        if (this.userName != null && !this.userName.equals(context.getUserName())) {
            return false;
        }
        return this.roles.isEmpty() || context.hasRoleIn(roles);
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
    
    boolean isGrantableBeforeProcessing(){
        return !isOwnershipRequired();
    }

    boolean isGrantedBeforeProcessing(Context context,CRUD operation){
        if(isOwnershipRequired()){
            return false;
        } else {
            return satisfiesBeforeProcessingProcessingConstraints(operation, context);
        }
    }

    boolean isGrantedAfterProcessing(Context context, Command command){
        if(satisfiesBeforeProcessingProcessingConstraints(CRUD.CREATE, context)){
            if(isOwnershipRequired()){
                final Object realSubject = command.getRealSubject();
                final ManagedTypeES type = command.getType();
                final Set<String> owners = type.getOwners(realSubject);
                return owners.contains(this.getUserName());
            } else {
                return true;
            }
        } else {
            return false;
        }
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
