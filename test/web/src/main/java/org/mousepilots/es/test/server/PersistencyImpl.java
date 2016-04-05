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
import org.mousepilots.es.test.shared.Persistency;
import org.mousepilots.es.test.domain.BaseEntity;

/**
 *
 * @author jgeenen
 */
@WebServlet("/Main/persistency")
public class PersistencyImpl extends RemoteServiceServlet implements Persistency {

    @Override
    public void submit(List<Command> commands) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends BaseEntity, ID> HasValue<T> get(int typeOrdinal, HasValue<ID> id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
