/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 * @param <EL>
 * @param <AD>
 */
public interface UpdatePluralAttribute<E, EL, A, AD extends PluralAttributeES<? super E,A,EL>,MS> extends UpdateAttribute<E,A,AD,MS>{
    
    
    
}
