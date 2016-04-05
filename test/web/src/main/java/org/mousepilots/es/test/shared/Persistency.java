/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.test.domain.BaseEntity;

/**
 *
 * @author jgeenen
 */
@RemoteServiceRelativePath("persistency")
public interface Persistency extends RemoteService{
    
    <T extends BaseEntity,ID> HasValue<T> get(int typeOrdinal, HasValue<ID> id);
     
    void submit(List<Command> commands);
}
