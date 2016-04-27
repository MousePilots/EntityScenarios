/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;

/**
 *
 * @author ap34wv
 * @param <T>
 */
abstract class AuthorizationBuilder<T> {
    
    private boolean requireOwnership=false;
    private String userName=null;
    private final Set<String> roles = new HashSet<>();
    private final Set<CRUD> operations = EnumSet.noneOf(CRUD.class);
    
    protected abstract Vertex getVertex();

    /**
     * Requires ownership of the managed instance on which the 
     * @return {@code this}
     */
    public AuthorizationBuilder<T> requireOwnership(){
        requireOwnership=true;
        return this;
    }
    
    /**
     * Specifies that the authorization is applicable only for the specified {@code userName}
     * @param userName
     * @return {@code this}
     */
    public AuthorizationBuilder<T> requireUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Specifies that the authorization being built applies only to caller principals with at least one of the specified {@code rols}
     * @param roles
     * @return {@code this}
     */
    public AuthorizationBuilder<T> requireOneOf(String... roles) {
        this.roles.addAll(Arrays.asList(roles));
        return this;
    }

    /**
     * Specifies that the authorization being built applies only to specified operations
     * @param operations
     * @return
     */
    public AuthorizationBuilder<T> forOperations(CRUD... operations) {
        this.operations.addAll(Arrays.asList(operations));
        return this;
    }

    protected final Authorization buildAuthorization() {
        if (operations.isEmpty()) {
            throw new IllegalStateException("the specification of one or more operations is mandatory");
        }
        return new Authorization(requireOwnership, userName, roles, operations);
    }
    
    public abstract T build();
}
