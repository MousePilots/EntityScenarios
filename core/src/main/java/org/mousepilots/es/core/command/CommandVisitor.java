/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.AttributeES;

/**
 *
 * @author geenenju
 * @param <O> return type upon visits
 * @param <I> argument-type upon visits
 */
public interface CommandVisitor<O,I>{
     
     <E> 
     O visit(CreateEmbeddable<E> create, I arg);
     
     <E,ID> 
     O visit(CreateEntity<E,ID> create, I arg);
     
     
     <E,A,AD extends AttributeES<?super E,A>> 
     O visit(UpdateEmbeddable<E,A,AD> update, I arg);
     
     <E, ID, V, A, AD extends AttributeES<? super E, A>> 
     O visit(UpdateEntity<E,ID,V,A,AD> update, I arg);

     <E,ID> 
     O visit(DeleteEntity<E,ID> delete, I arg);
}
