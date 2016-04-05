/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.util.Framework;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 * The server-side transaction-scoped context in which {@link Command}s are executed.
 * @author geenenju
 */
@GwtIncompatible
public interface ServerContext extends Context<EntityManager>{

    /**
     * @param <E> the entity's java-type
     * @param <I> the entity-id's java-type
     * @param type the entity's type
     * @param clientId the entity's id as known on the client initiating the transaction {@code this} is the servercontext for
     * @return the entity's true id as stored or assigned in the current server-side transaction
     * @throws NullPointerException if {@code type==null || clientId==null}
     */
    <E, I> I getServerId(IdentifiableTypeES<? super E> type, I clientId) throws NullPointerException;


    /**
     * Invoked immedeately after execution on {@code this}' server of the {@code command}
     * @param <E> the entity's java-type
     * @param <I> the entity-id's java-type
     * @param command
     */
    @Framework
    <E, I> void onExecuteOnServer(Command command);

    /**
     * Finds the entity of the given {@code type}, with the specified
     * {@code clientId}
     *
     * @param <E>
     * @param <ID>
     * @param type
     * @param clientId
     * @return the entity
     */
    default <E, ID> E find(IdentifiableTypeES<E> type, ID clientId) {
        final ID serverId = getServerId(type, clientId);
        return getEntityManager().find(type.getJavaType(), serverId);
    }
    
}
