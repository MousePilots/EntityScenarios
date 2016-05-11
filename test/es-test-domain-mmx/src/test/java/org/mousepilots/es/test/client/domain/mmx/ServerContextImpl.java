/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client.domain.mmx;

import org.mousepilots.es.test.server.domain.mmx.JPA;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.scenario.AbstractServerContext;

/**
 *
 * @author ap34wv
 */
public class ServerContextImpl extends AbstractServerContext{
    
    private final EntityManager entityManager = JPA.createEntityManager();

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return true;
    }



    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
