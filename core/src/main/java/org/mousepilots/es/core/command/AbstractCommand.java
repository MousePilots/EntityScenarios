/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.util.concurrent.atomic.AtomicInteger;
import org.mousepilots.es.core.command.ClientState;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <TD>
 */
abstract class AbstractCommand<T, TD extends ManagedTypeES<T>> implements Command<T, TD>, Comparable<AbstractCommand> {

     private static final AtomicInteger COMMAND_ID_GENERATOR = new AtomicInteger(0);

     private int commandId;

     private long creationDateTime;

     private transient EntityManagerESImpl clientEntityManager;

     private int typeOrdinal;

     private ClientState clientState;

     private transient Proxy<T> proxy;

     private transient T subject;

     protected AbstractCommand() {
     }

     protected AbstractCommand(EntityManagerESImpl clientEntityManager, TD typeDescriptor) {
          this.clientEntityManager = clientEntityManager;
          this.typeOrdinal = typeDescriptor.getOrdinal();
          this.clientState = ClientState.CREATED;
          this.creationDateTime = System.currentTimeMillis();
          this.commandId = AbstractCommand.COMMAND_ID_GENERATOR.getAndIncrement();
     }

     protected final EntityManagerESImpl getClientEntityManager() {
          return clientEntityManager;
     }

     public final int getCommandId() {
          return commandId;
     }

     public final long getCreationDateTime() {
          return creationDateTime;
     }

     @Override
     public final ClientState getClientState() {
          return clientState;
     }

     private void setClientState(ClientState clientState) {
          if (this.clientState.getNext() == clientState) {
               this.clientState = clientState;
          } else {
               throw new IllegalArgumentException("cannot perform client-state change " + this.clientState + " --> " + clientState);
          }
     }

     @Override
     public final TD getType() {
          return (TD) AbstractMetamodelES.getInstance().getType(typeOrdinal);
     }

     @Override
     public final Proxy<T> getProxy() {
          return proxy;
     }

     /**
      * Sets the client-side proxy
      *
      * @param proxy
      * @throws IllegalStateException if allready set
      */
     protected final void setProxy(Proxy<T> proxy) throws IllegalStateException {
          if (this.proxy == null) {
               this.proxy = proxy;
          } else {
               throw new IllegalStateException("proxy was set allready");
          }
     }

     @Override
     public final T getRealSubject() {
          return subject;
     }

     /**
      * Sets the server-side subject of {@code this} change
      *
      * @param subject
      * @throws IllegalStateException if allready set and {@code subject!=null}
      */
     protected final void setRealSubject(T subject) throws IllegalStateException {
          if (this.subject == null || subject == null) {
               this.subject = subject;
          } else {
               throw new IllegalStateException("proxy was set allready");
          }
     }

     private void performTransition(ClientState nextState, Runnable executable) {
          final ClientState previousState = this.clientState;
          clientEntityManager.beforeStateChange(this, nextState);
          executable.run();
          setClientState(nextState);
          clientEntityManager.afterStateChange(this, previousState);
     }

     protected abstract void doExecuteOnClient();

     protected abstract void doUndo();

     protected abstract void doRedo();

     @Override
     public final void executeOnClient() {
          performTransition(ClientState.EXECUTED, this::doExecuteOnClient);
     }

     @Override
     public final void undoOnClient() {
          performTransition(ClientState.UNDONE, this::doUndo);
     }

     @Override
     public final void redoOnClient() {
          performTransition(ClientState.REDONE, this::doRedo);
     }

     @Override
     public final int compareTo(AbstractCommand o) {
          return Integer.compare(this.commandId, o.commandId);
     }

     @Override
     public final int hashCode() {
          int hash = 7;
          hash = 67 * hash + this.commandId;
          hash = 67 * hash + (int) (this.creationDateTime ^ (this.creationDateTime >>> 32));
          return hash;
     }

     @Override
     public final boolean equals(Object obj) {
          return this==obj;
     }
}
