/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server;

import javax.persistence.EntityManager;

/**
 *
 * @author clevenro
 */
public interface HasEntityManager {

    EntityManager getEntityManager();

    void setEntityManager(EntityManager em);
}
