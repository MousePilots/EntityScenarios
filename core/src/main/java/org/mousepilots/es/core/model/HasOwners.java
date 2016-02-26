/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import java.util.Collection;

/**
 * Functional interface which providers the usernames of the owners of an entity.
 * @author jgeenen
 * @param <C> some {@link Collection} of {@link String}s
 */
public interface HasOwners<C extends Collection<String>> {
    /**
     * @return the usernames of the owners of an entity
     */
    C getOwners();
}
