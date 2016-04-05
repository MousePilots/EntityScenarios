/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.util.GwtIncompatible;
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author geenenju
 */
@GwtIncompatible
public abstract class AbstractServerContext implements ServerContext{

    private final Map<IdentifiableTypeES, Map<Object, Object>> type2clientId2serverId = new HashMap<>();

    /**
     * 
     * @param <E> the entity's java-type
     * @param <I> the entity-id's java-type
     * @param type the entity's type
     * @param clientId the entity's id as known on the client initiating the transaction {@code this} is the servercontext for
     * @return the entity's true id as stored or assigned in the current server-side transaction
     * @throws NullPointerException if {@code type==null || clientId==null}
     */
    @Override
    public <E,I> I getServerId(IdentifiableTypeES<? super E> type, I clientId) throws NullPointerException{
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(clientId,"clientId must not be null");
        final I serverId = Maps.<I>get(type2clientId2serverId, type, clientId);
        return serverId == null ? clientId : serverId;
    }

    /**
     * Callback method invoked by {@link Command#executeOnServer(org.mousepilots.es.core.scenario.ServerContext)}.
     * @param <E> the entity's java-type
     * @param <I> the entity-id's java-type
     * @param command
     */
    @Override
    public <E,I> void onExecuteOnServer(Command command) {
        if(command instanceof CreateEntity){
            CreateEntity<E,I> createEntity = (CreateEntity<E,I>) command;
            final EntityTypeES type = createEntity.getType();
            final Map<Object, Object> clientId2serverId = Maps.getOrCreate(type2clientId2serverId, type,HashMap::new);
            final I clientId = createEntity.getId();
            final I serverId = createEntity.getServerId();
            clientId2serverId.put(clientId, serverId);
        }
    }


}
