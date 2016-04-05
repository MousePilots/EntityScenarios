/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Collection;


/**
 *
 * @author jgeenen
 * @param <EM> the entity-manager type
 */
public interface Context<EM> {

     String getUserName();

     Collection<String> getRoles();

     EM getEntityManager();

}
