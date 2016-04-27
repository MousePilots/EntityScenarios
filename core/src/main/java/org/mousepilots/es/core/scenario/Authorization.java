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
import org.mousepilots.es.core.util.StringUtils;

/**
 *
 * @author ap34wv
 */
public class Authorization implements Serializable{
     
    private final boolean ownershipRequired;
    
    private final String userName;
    
    private final Set<String> roles;
    
    private final Set<CRUD> operations;
    
    protected Authorization(boolean ownershipRequired, String userName, Set<String> roles, Set<CRUD> operations) {
        this.ownershipRequired = ownershipRequired;
        this.userName = userName;
        this.roles = Collections.unmodifiableSet(roles);
        this.operations = Collections.unmodifiableSet(operations);
    }

    public boolean isOwnershipRequired() {
        return ownershipRequired;
    }
    
    public String getUserName() {
        return userName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<CRUD> getOperations() {
        return operations;
    }
    
    public boolean isGranted(Context context,CRUD operation) {
        if (!operations.contains(operation)) {
            return false;
        }
        if (this.userName != null && !this.userName.equals(context.getUserName())) {
            return false;
        }
        return this.roles.isEmpty() || context.hasRoleIn(roles);
    }

    @Override
    public String toString() {
        return StringUtils.createToString(
            getClass(), Arrays.asList(
                "userName", userName,
                "roles", "[" + StringUtils.join(roles, r -> r, ",") + "]",
                "operations", "[" + StringUtils.join(operations, o -> o.toString(), ",") + "]"
            )
        );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.userName);
        hash = 59 * hash + Objects.hashCode(this.roles);
        hash = 59 * hash + Objects.hashCode(this.operations);
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
