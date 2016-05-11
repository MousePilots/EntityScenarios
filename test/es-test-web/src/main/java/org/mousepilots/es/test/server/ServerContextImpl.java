/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.util.Set;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.scenario.AbstractServerContext;

/**
 *
 * @author geenenju
 */
public class ServerContextImpl extends AbstractServerContext{
    
    private String userName;
    
    private Set<String> roles;

    private final EntityManager entityManager;

    public ServerContextImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    

    @Override
    public boolean isUserInRole(String role) {
        return this.roles!=null && this.roles.contains(role);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
