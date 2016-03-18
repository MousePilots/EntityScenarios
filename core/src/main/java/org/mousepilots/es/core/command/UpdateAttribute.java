/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.io.Serializable;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E> type of the entity/embeddable on which the attribute is defined
 * @param <A> the attribute-javatype
 * @param <AD> the attribute-type
 */
public interface UpdateAttribute<E, A, AD extends AttributeES<? super E, A>,MS> extends Serializable{
     
     void executeOnClient(Update<E,?,A,AD,?> update);
     
     void undo(Update<E,?,A,AD,?> update);
     
     
    default A getAttributeValueOnClient(Update<E, ?, A, AD, ?> update) {
        return update.getAttribute().getJavaMember().get(update.getProxy().__subject());
    }

    default A getAttributeValueOnServer(Update<E, ?, A, AD, ?> update) {
        return update.getAttribute().getJavaMember().get(update.getRealSubject());
    }
     
     
     default void redo(Update<E,?,A,AD,?> update){
          executeOnClient(update);
     }
     
     MS getModificationOnServer(ServerContext serverContext);
     
     void executeOnServer(Update<E,?,A,AD,?> update, ServerContext serverContext);
     
     
     <R,A> R accept(UpdateAttributeVisitor<R,A> visitor, A arg);
     
}
