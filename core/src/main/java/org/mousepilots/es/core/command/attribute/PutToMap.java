/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.Embeddables;
import org.mousepilots.es.core.command.UpdateAttributeVisitor;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.command.attribute.value.ValueFactory;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.Getter;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 */
public final class PutToMap<E, K, V> extends UpdateMap<E, K, V, Map<K,V>> {

    private transient Map<K, V> putAlls = null;

    private transient Map<K, V> replaced = null;
    
    private Map<Value<K, K, ?, ?>, Value<V, V, ?, ?>> putAllValues;
    
    /**
     *
     * @param <T>
     * @param entries
     * @param keyOrValueGetter a function for extracting either
     * {@link Entry#getKey()} or {@link Entry#getValue()}
     */
    protected final <T> void asserAllNewEmbeddables(Set<? extends Entry<K, V>> entries, Getter<Entry<K, V>, Proxy<T>> keyOrValueGetter) {
        for (Entry<K, V> entry : entries) {
            final Proxy<T> proxy = keyOrValueGetter.invoke(entry);
            Embeddables.assertIfEmbeddableThenCreated(proxy);
        }
    }

    private PutToMap(){}
    
    
    PutToMap(MapAttributeESImpl<? super E, K, V> attribute, final Map<K, V> map, final Map<K,V> putAlls) {
        super();
        final Set<Entry<K, V>> putAllEntries = putAlls.entrySet();
        final TypeES<K> keyType = attribute.getKeyType();
        final TypeES<V> valueType = attribute.getElementType();
        if (keyType instanceof EmbeddableTypeES) {
            asserAllNewEmbeddables(putAllEntries, e -> (Proxy<K>) e.getKey());
        }
        if (valueType instanceof EmbeddableTypeES) {
            asserAllNewEmbeddables(putAllEntries, e -> (Proxy<V>) e.getValue());
        }
        this.putAllValues = new HashMap<>();
        for (Entry<K, V> entry : putAllEntries) {
            final K key = entry.getKey();
            final V newValue = entry.getValue();
            if(map.containsKey(key)){
                final V oldValue = map.get(key);
                if (Objects.equals(oldValue, newValue)){
                    //no new entry
                    continue;
                } else {
                    replaced.put(key, oldValue);
                }
            }
            final Value<K, K, ?, ?> keyValue = ValueFactory.create(keyType, key);
            final Value<V, V, ?, ?> newValueValue = ValueFactory.create(valueType, newValue);
            putAllValues.put(keyValue, newValueValue);
        }
    }

    @Override
    public void executeOnClient(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
        getAttributeValueOnClient(update).putAll(putAlls);
    }

    @Override
    public void undo(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
        final Map<K, V> map = getAttributeValueOnClient(update);
        map.keySet().removeAll(putAlls.keySet());
        map.putAll(replaced);
    }
    
    /**
     * 
     * @param serverContext
     * @return the entries put by {@code this}
     */
    @Override @GwtIncompatible
    public Map<K, V> getModificationOnServer(ServerContext serverContext) {
        if(putAlls==null){
            final Map<K,V> modifiableServerValues = new HashMap<>();
            for(Entry<Value<K, K, ?, ?>, Value<V, V, ?, ?>> entry : this.putAllValues.entrySet()){
                final K key = entry.getKey().getServerValue(serverContext);
                final V value = entry.getValue().getServerValue(serverContext);
                modifiableServerValues.put(key, value);
            }
            this.putAlls = Collections.unmodifiableMap(modifiableServerValues);
        }
        return putAlls;
    }
    
    

    @Override @GwtIncompatible
    public void executeOnServer(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update, ServerContext serverContext){
        getAttributeValueOnServer(update).keySet().removeAll(getModificationOnServer(serverContext).keySet());
    }

    @Override
    public <R, A> R accept(UpdateAttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    

}
