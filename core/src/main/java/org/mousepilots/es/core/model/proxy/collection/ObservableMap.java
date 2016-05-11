/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Proxy for a Java 7-{@link Map}.
 *
 * @author geenenju
 * @param <K>
 * @param <V>
 */
public class ObservableMap<K, V> extends Observable<Map<K, V>, MapObserver> implements Map<K, V> {

    public ObservableMap() {
        super(new HashMap<>());
    }

    public ObservableMap(Map<K, V> delegate) {
        super(delegate);
    }

    @Override
    protected Map<K, V> createUnmodifiable(Map<K, V> delegate) {
        return Collections.unmodifiableMap(delegate);
    }

    @Override
    public int size() {
        return getDelegate().size();
    }

    @Override
    public boolean isEmpty() {
        return getDelegate().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getDelegate().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getDelegate().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return getDelegate().get(key);
    }

    @Override
    public final V put(K key, V value) {
        fire(l -> l.onPut(getDelegate(), key, value));
        return getDelegate().put(key, value);
    }

    @Override
    public final V remove(Object key) {
        fire(l -> l.onRemove(createUnmodifiable(getDelegate()), key));
        return getDelegate().remove(key);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        fire(l -> l.onPutAll(createUnmodifiable(getDelegate()), m));
        getDelegate().putAll(m);
    }

    @Override
    public final void clear() {
        fire(l -> l.onClear(createUnmodifiable(getDelegate())));
        getDelegate().clear();
    }

    /**
     * @return {@code this}' unmodifiable {@link Map#keySet()}
     */
    @Override
    public final Set<K> keySet() {
        return Collections.unmodifiableSet(getDelegate().keySet());
    }

    /**
     * @return {@code this}' unmodifiable {@link Map#values()}
     */
    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(getDelegate().values());
    }

    /**
     *
     * @return {@code this}' unmodifiable {@link Map#entrySet()}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(getDelegate().entrySet());
    }

    @Override
    public boolean equals(Object o) {
        return getDelegate().equals(o);
    }

    @Override
    public int hashCode() {
        return getDelegate().hashCode();
    }

}
