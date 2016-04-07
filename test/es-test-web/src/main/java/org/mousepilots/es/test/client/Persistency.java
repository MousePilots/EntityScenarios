/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.HasValue;

/**
 *
 * @author jgeenen
 */
@RemoteServiceRelativePath("persistency")
public interface Persistency extends RemoteService{
    
    <T> HasValue<T> get(int typeOrdinal, HasValue id);
     
    void submit(List<Command> commands);
}
