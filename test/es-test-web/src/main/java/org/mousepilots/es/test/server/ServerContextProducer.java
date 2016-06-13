/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.test.server.domain.mmx.JPA;

/**
 *
 * @author bhofsted
 */
@SessionScoped
public class ServerContextProducer implements Serializable{
    
    @Produces
    public ServerContext produce(){
        return new ServerContextImpl(JPA.createEntityManager());
    }
    
}
