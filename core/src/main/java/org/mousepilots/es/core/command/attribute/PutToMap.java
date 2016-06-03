/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.Collection;
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
public final class PutToMap<E, K, V> extends UpdateMap<E, K, V, Map<K, V>> {

    private transient Map<K, V> putAlls = null;

    private transient Map<K, V> replaced = new HashMap<>();

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

    private PutToMap() {
    }

    PutToMap(MapAttributeESImpl<? super E, K, V> attribute, final Map<K, V> map, final Map<K, V> putAlls) {
        super();
        this.putAlls = putAlls;
        final Set<Entry<K, V>> putAllEntries = putAlls.entrySet();
        final TypeES<K> keyType = attribute.getKeyType();
        final TypeES<V> valueType = attribute.getElementType();
        if (keyType instanceof EmbeddableTypeES) {
            for (Proxy<K> key : (Collection<Proxy<K>>) putAlls.keySet()) {
                if (!key.__getProxyAspect().isCreated() && !map.containsKey((K) key)) {
                    throw new IllegalStateException(key + "is no new or pre-existing key");
                }
            }
        }
        if (valueType instanceof EmbeddableTypeES) {
            asserAllNewEmbeddables(putAllEntries, e -> (Proxy<V>) e.getValue());
        }
        this.putAllValues = new HashMap<>();
        for (Entry<K, V> entry : putAllEntries) {
            final K key = entry.getKey();
            final V newValue = entry.getValue();
            if (map.containsKey(key)) {
                final V oldValue = map.get(key);
                if (Objects.equals(oldValue, newValue)) {
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
        getDelegate(update).putAll(putAlls);
    }

    @Override
    public void undo(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update) {
        final Map<K, V> delegate = getDelegate(update);
        delegate.keySet().removeAll(putAlls.keySet());
        delegate.putAll(replaced);
    }

    @Override
    @GwtIncompatible
    protected Map<K, V> doGetModificationOnServer(ServerContext serverContext) {
        final Map<K, V> modificationOnServer = new HashMap<>();
        for (Entry<Value<K, K, ?, ?>, Value<V, V, ?, ?>> entry : this.putAllValues.entrySet()) {
            final K key = entry.getKey().getServerValue(serverContext);
            final V value = entry.getValue().getServerValue(serverContext);
            modificationOnServer.put(key, value);
        }
        return Collections.unmodifiableMap(modificationOnServer);

    }

    @Override
    @GwtIncompatible
    public void executeOnServer(Update<E, ?, Map<K, V>, MapAttributeESImpl<? super E, K, V>, ?> update, ServerContext serverContext) {
        final Map<K, V> modificationOnServer = getModificationOnServer(serverContext);
        getNonNullAttributeValueOnServer(update).putAll(modificationOnServer);
    }

    @Override
    public <R, A> R accept(UpdateAttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

}
