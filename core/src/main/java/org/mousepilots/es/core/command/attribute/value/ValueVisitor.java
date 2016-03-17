/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

/**
 *
 * @author geenenju
 */
public interface ValueVisitor<R,A> {
     
     <T>    R visit(BasicValue<T> basicValue,A arg);
     
     <E>    R visit(ExistingEmbeddableValue<E> embeddableValue, A arg);
     
     <E,ID> R visit(ExistingEntityValue<E,ID> entityValue, A arg);
     
     <E>    R visit(NewManagedValue<E,?> managedValue, A arg);
     
}
