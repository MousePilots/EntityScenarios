/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.test.client.Persistency;

/**
 *
 * @author jgeenen
 */
@WebServlet("/Main/persistency")
public class PersistencyImpl extends RemoteServiceServlet implements Persistency {
    
    @Inject
    PersistencyBean delegate;

    @Override
    public void submit(List<Command> commands) {
        delegate.submit(commands);
    }

    @Override
    public <T> HasValue<T> get(int typeOrdinal, HasValue id) {
        return delegate.<T>get(typeOrdinal, id);
    }
    
    
}
