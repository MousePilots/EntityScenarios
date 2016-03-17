/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Collection;
import javax.persistence.EntityManager;


/**
 *
 * @author jgeenen
 * @param <E>
 */
public interface Context<E extends EntityManager> {

     String getUserName();

     Collection<String> getRoles();

     E getEntityManager();

}
