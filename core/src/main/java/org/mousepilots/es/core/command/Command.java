/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.io.Serializable;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <TD>
 */
public interface Command<T,TD extends ManagedTypeES<T>> extends Serializable{
     
     ClientState getClientState();
     
     /**
      * @return the {@code subject}'s type
      */
     TD getType();
     
     /**
      * Gets the transient client-side proxy for the server-side managed instance subject to {@code this}.
      * Depending on the type of command, the proxy is unavailable before {@code this.executeOnClient()}.
      * @return the client-side proxy for the server-side managed instance subject to {@code this}
      */
     Proxy<T> getProxy();
     
     /**
      * @return the transient server-side managed instance subject to {@code this} operation
      */
     T getRealSubject();
     
     /**
      * @return the type of operation
      */
     CRUD getOperation();
     
     /**
      * executes {@code this} on the client: musn't be called on the server
      */
     void executeOnClient();
     
     /**
      * redoes {@code this} on the client: musn't be called on the server
      */
     void redoOnClient();
     
     /**
      * Undoes {@code this.executeOnClient()} on the client: musn't be called on the server
      */
     void undoOnClient();
     
     /**
      * executes {@code this} on the server: musn't be called on the client
      * @param serverContext 
      */
     void executeOnServer(final ServerContext serverContext);
     
     /**
      * Accepts a {@link CommandVisitor}
      * @param <R>
      * @param <A>
      * @param visitor
      * @param arg
      * @return {@code visitor.visit(this,arg)}
      */
     <R,A> R accept(CommandVisitor<R,A> visitor, A arg);
     
}
