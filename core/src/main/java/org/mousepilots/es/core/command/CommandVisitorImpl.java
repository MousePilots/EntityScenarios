/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.AttributeES;

/**
 * Empty implementation of {@link CommandVisitor}. Each method is empty except for returning {@code null}.
 * @author geenenju
 * @param <O>
 * @param <I>
 */
public class CommandVisitorImpl<O,I> implements CommandVisitor<O, I>{

    @Override
    public <E> O visit(CreateEmbeddable<E> create, I arg) {
        return null;
    }

    @Override
    public <E, ID> O visit(CreateEntity<E, ID> create, I arg) {
        return null;
    }

    @Override
    public <E, ID> O visit(DeleteEntity<E, ID> create, I arg) {
        return null;
    }

    @Override
    public <E, A, AD extends AttributeES<? super E, A>> O visit(UpdateEmbeddable<E, A, AD> update, I arg) {
        return null;
    }

    @Override
    public <E, ID, V, A, AD extends AttributeES<? super E, A>> O visit(UpdateEntity<E, ID, V, A, AD> update, I arg) {
        return null;
    }
    
}
