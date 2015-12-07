package org.mousepilots.es.core.model;

import java.io.Serializable;

/**
 * Generator class that can generate keys for entities that can be used while they are not persisted yet.
 * For example: when a new instance of an entity is created and does not have a key from the database.
 * @param <T> The generated value's type.
 * @see Serializable
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface Generator<T extends Serializable>{

    /**
     * Generate a new key for the specified type.
     * @return A new generated key for the specified type.
     */
    T generate();
    
    /**
     * Reset the generator.
     */
    void reset();
}