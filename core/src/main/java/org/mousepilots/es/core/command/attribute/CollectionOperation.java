/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.LinkedList;
import java.util.Collection;

/**
 * Provides basic operations on {@link Collection}s
 * @author geenenju
 */
public enum CollectionOperation {
    /**
     * Adds values to a {@link Collection}
     */
    ADD() {
        @Override
        public CollectionOperation inverse() {
            return REMOVE;
        }

        @Override
        public <T> boolean valueModifiesCollection(Collection<T> collection, T value) {
            return !collection.contains(value);
        }

        @Override
        public <T> void execute(Collection<T> collection, Collection<T> values) {
            collection.addAll(values);
        }

    }, 
    /**
     * Removes values from a {@link Collection}
     */
    REMOVE() {
        @Override
        public CollectionOperation inverse() {
            return ADD;
        }

        @Override
        public <T> boolean valueModifiesCollection(Collection<T> collection, T value) {
            return collection.contains(value);
        }

        @Override
        public <T> void execute(Collection<T> collection, Collection<T> values) {
            collection.removeAll(values);
        }

    };

    public abstract CollectionOperation inverse();

    /**
     * 
     * @param <T>
     * @param collection
     * @param value
     * @return whether or not {@code this} operation on the {@code collection} with the specified {@code value} modifies the {@code collection}
     */
    public abstract <T> boolean valueModifiesCollection(Collection<T> collection, T value);

    public final <T> LinkedList<T> getNet(Collection<T> collection, Collection<T> values) {
        LinkedList<T> retval = new LinkedList<>();
        for (T mod : values) {
            if (valueModifiesCollection(collection, mod)) {
                retval.add(mod);
            }
        }
        return retval;
    }

    /**
     * Modifies the {@code collection} with the specified {@code values}
     *
     * @param <T>
     * @param collection
     * @param values
     */
    public abstract <T> void execute(Collection<T> collection, Collection<T> values);

}
