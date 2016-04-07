/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.HasValue;

/**
 *
 * @author geenenju
 */
public interface PersistencyAsync {
    
    <T> void get(int typeOrdinal, HasValue id, AsyncCallback<HasValue<T>> callback);
     
    void submit(List<Command> commands, AsyncCallback<Void> callback);

}
