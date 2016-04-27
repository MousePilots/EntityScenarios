/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client.domain.mmx;

import java.util.List;
import javax.persistence.EntityTransaction;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author ap34wv
 */
public class CommandProcessor {
    
    private final ServerContext serverContext = new ServerContextImpl();

    public ServerContext getServerContext() {
        return serverContext;
    }
    
    public void process(List<Command> commands){
        final EntityTransaction transaction = serverContext.getEntityManager().getTransaction();
        transaction.begin();
        commands.stream().forEach((command) -> {
            command.executeOnServer(serverContext);
        });
        transaction.commit();
    }
    
}
