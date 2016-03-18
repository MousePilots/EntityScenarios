/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.util.Collection;
import org.mousepilots.es.core.command.attribute.PutToMap;
import org.mousepilots.es.core.command.attribute.RemoveFromMap;
import org.mousepilots.es.core.command.attribute.UpdateCollection;
import org.mousepilots.es.core.command.attribute.UpdateSingularAttribute;
import org.mousepilots.es.core.model.PluralAttributeES;

/**
 *
 * @author geenenju
 */
public interface UpdateAttributeVisitor<O,I> {
    
    <E,A> 
    O visit(UpdateSingularAttribute<E,A> u, I arg);
    
    <E,EL,A extends Collection<EL>, AD extends PluralAttributeES<? super E, A, EL>>
    O visit(UpdateCollection<E,EL,A, AD> u, I arg);
    
    <E, K, V>
    O visit(PutToMap<E, K, V> u, I arg);
    
    <E, K, V>
    O visit(RemoveFromMap<E, K, V> u, I arg);
    
}
