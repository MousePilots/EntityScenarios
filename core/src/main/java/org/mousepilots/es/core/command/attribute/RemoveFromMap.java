/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttributeVisitor;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.command.attribute.value.ValueFactory;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 */
public final class RemoveFromMap<E, K, V>  extends UpdateMap<E, K, V, Set<K>> {
    
    private transient Map<K,V> clientRemoved;
    
    private LinkedList<Value<K,K,?,?>> removals = new LinkedList<>();
    
    private transient Set<K> modification;

    private RemoveFromMap(){}
    
    /**
     * 
     * @param attribute the updated attribute
     * @param map the updated map
     * @param keys the net removals: must be contained in {@code map.keySet()}
     * @throws IllegalArgumentException if{@code !map.keySet().containsAll(keys)}
     */
    RemoveFromMap(MapAttributeESImpl<? super E, K, V> attribute, final Map<K, V> map, Set<K> keys) throws IllegalArgumentException{
        if(map.keySet().containsAll(keys)){
            this.clientRemoved = new HashMap<>();
            final TypeES<K> keyType = attribute.getKeyType();
            for(K key : keys){
                final V value = map.get(key);
                this.clientRemoved.put(key, value);
                this.removals.add(ValueFactory.create(keyType, key));
            }
        } else {
            throw new IllegalArgumentException("keys is not contained in map.getKeySet()");
        }
    }
    
    @Override
    public void executeOnClient(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
        final Map<K, V> map = getAttributeValueOnClient(update);
        map.keySet().removeAll(clientRemoved.keySet());
    }

    @Override
    public void undo(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
        getAttributeValueOnClient(update).putAll(clientRemoved);
    }

    /**
     * 
     * @param serverContext
     * @return the set of keys (to be) removed from the map by {@code this}
     */
    @Override @GwtIncompatible
    public Set<K> getModificationOnServer(ServerContext serverContext) {
        if(modification==null){
            final Set<K> modifiableModification = new HashSet<>();
            for(Value<K,K,?,?> removal : this.removals){
                modifiableModification.add(removal.getServerValue(serverContext));
            }
            this.modification = Collections.unmodifiableSet(modifiableModification);
        }
        return modification;
    }

    @Override @GwtIncompatible
    public void executeOnServer(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update, ServerContext serverContext) {
        final Map<K, V> map = getNonNullAttributeValueOnServer(update);
        final Set<K> keySet = map.keySet();
        keySet.removeAll(getModificationOnServer(serverContext));
    }
    
    @Override
    public <R, A> R accept(UpdateAttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
    
}
