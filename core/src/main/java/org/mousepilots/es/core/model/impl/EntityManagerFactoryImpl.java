/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import org.mousepilots.es.core.model.EntityManagerES;

/**
 *
 * @author jgeenen
 */
public class EntityManagerFactoryImpl implements org.mousepilots.es.core.model.EntityManagerFactory {

    @Override
    public EntityManagerES createEntityManager() {
        return new EntityManagerImpl(this);
    }
    
}
