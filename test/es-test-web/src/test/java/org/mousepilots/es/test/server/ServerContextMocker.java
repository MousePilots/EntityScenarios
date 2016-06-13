/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.util.Collection;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author bhofsted
 */
public class ServerContextMocker {

    public static ServerContext getServerContext() {
        return (new ServerContext() {
            @Override
            public String getUserName() {
                return "bill"; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public boolean hasRoleIn(Collection<String> roles) {
                return false;
            }

            @Override
            public EntityManager getEntityManager() {
                return null;
            }

            @Override
            public <E, I> I getServerId(IdentifiableTypeES<? super E> type, I clientId) throws NullPointerException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <E, I> void onExecuteOnServer(Command command) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
