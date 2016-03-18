/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Map;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <K>
 * @param <V>
 * @param <MS>
 */
public abstract class UpdateMap<E, K, V, MS> implements UpdatePluralAttribute<E, V, Map<K, V>, MapAttributeESImpl<? super E, K, V>, MS> {
    
    protected UpdateMap(){}
    
}
