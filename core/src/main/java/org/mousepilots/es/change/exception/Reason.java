/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.exception;

/**
 *
 * @author Roy Cleven
 */
public enum Reason {
    /**
     * An entity with the same key already exists.
     */
    ENTITY_EXISTS,
    /**
     * No entity with the key currently exists.
     */
    ENTITY_NOT_FOUND,
    /**
     * Version of used entity doesn't match with stored entity.
     */
    VERSION_MISMATCH,
    /**
     * Value already exists in collection.
     */
    DUPLICATE_VALUE,
    /**
     * Collection doesn't contain the given value.
     */
    NO_SUCH_VALUE,
    /**
     * Map doesn't contain an entry for the given key.
     */
    NO_ENTRY_FOR_KEY,
    /**
     * Map doesn't contain an entry for the given key and value.
     */
    NO_ENTRY_FOR_KEY_AND_VALUE,
    /**
     * Map already contains an entry for the given key.
     */
    KEY_ALREADY_PRESENT
}
