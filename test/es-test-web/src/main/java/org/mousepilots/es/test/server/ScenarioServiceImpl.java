/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.test.client.ScenarioService;

/**
 *
 * @author jgeenen
 */
@WebServlet("/Main/scenario")
public class ScenarioServiceImpl extends RemoteServiceServlet implements ScenarioService {
    
    ScenarioServiceBean delegate = new ScenarioServiceBean();

    @Override
    public void submit(List<Command> commands) {
        delegate.submit(commands);
    }

    @Override
    public HasValue get(int typeOrdinal, HasValue id) {
        return delegate.get(typeOrdinal, id);
    }
    
    
}
