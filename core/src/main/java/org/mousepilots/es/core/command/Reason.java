/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

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
     * The entry which is being added contains a null key..
     */
    KEY_IS_NULL,
    /**
     * The selected {@link DtoType} is not yet supported
     */
    DTO_TYPE_NOT_SUPPORTED,
    /**
     * The selected {@link DtoType} is not matching with other {@link Change}'s
     */
    NOT_MATCHING_DTO_TYPE
}
