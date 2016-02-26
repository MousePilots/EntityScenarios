/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

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
        return subject.size();
    }

    @Override
    public boolean isEmpty() {
        return subject.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return subject.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return subject.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return subject.get(key);
    }

    @Override
    public final V put(K key, V value) {
        fire(l -> l.onPut(subject, key, value));
        return subject.put(key, value);
    }

    @Override
    public final V remove(Object key) {
        fire(l -> l.onRemove(createUnmodifiable(subject), key));
        return subject.remove(key);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        fire(l -> l.onPutAll(createUnmodifiable(subject), m));
        subject.putAll(m);
    }

    @Override
    public final void clear() {
        fire(l -> l.onClear(createUnmodifiable(subject)));
        subject.clear();
    }

    /**
     * @return {@code this}' unmodifiable {@link Map#keySet()}
     */
    @Override
    public final Set<K> keySet() {
        return Collections.unmodifiableSet(subject.keySet());
    }

    /**
     * @return {@code this}' unmodifiable {@link Map#values()}
     */
    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(subject.values());
    }

    /**
     *
     * @return {@code this}' unmodifiable {@link Map#entrySet()}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(subject.entrySet());
    }

    @Override
    public boolean equals(Object o) {
        return subject.equals(o);
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }

}
