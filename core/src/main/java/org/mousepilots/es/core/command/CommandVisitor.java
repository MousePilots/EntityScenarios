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
 */
public interface CommandVisitor<O,I>{
     
     <E> 
     O visit(CreateEmbeddable<E> create, I arg);
     
     <E,ID> 
     O visit(CreateEntity<E,ID> create, I arg);
     
     <E,ID> 
     O visit(DeleteEntity<E,ID> create, I arg);
     
     <E,A,AD extends AttributeES<?super E,A>> 
     O visit(UpdateEmbeddable<E,A,AD> update, I arg);
     
     <E, ID, V, A, AD extends AttributeES<? super E, A>> 
     O visit(UpdateEntity<E,ID,V,A,AD> update, I arg);
}
