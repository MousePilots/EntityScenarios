/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.container.Container;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <T>
 */
public final class CreateEmbeddable<T> extends Create<T, EmbeddableTypeES<T>> implements EmbeddableTypeCommand<T> {
    
    private Container container;
    
    private CreateEmbeddable() {
        super();
    }
    
    public CreateEmbeddable(EntityManagerImpl entityManager, EmbeddableTypeES<T> typeDescriptor) {
        super(typeDescriptor, entityManager);
    }
    
    @Override
    public Container getContainer() {
        return container;
    }
    
    @Override
    public void setContainer(Container container) {
        EmbeddableTypeCommand.super.setContainer(container);
        this.container = container;
    }
    
    @Override
    public <R, A> R accept(CommandVisitor<R, A> listener, A arg) {
        return listener.visit(this, arg);
    }
    
    @Override @GwtIncompatible
    public void executeOnServer(ServerContext serverContext) {
        super.executeOnServer(serverContext);
        serverContext.onExecuteOnServer(this);
    }
    
}
