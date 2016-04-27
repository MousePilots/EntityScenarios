/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import java.util.Set;

/**
 * Provides the usernames owning an instance of a {@link ManagedTypeES}'s java type managed by a server-side entity manager. The latter allows your implementation of {@link #getOwners()} to traverse
 * associations or otherwise lazily loaded properties.
 * @author jgeenen
 */
public interface HasOwners{
    
    public static final String ANNOTATION_NAME="ProvidesOwners";
    
    /**
     * 
     * @return the user-names of the users owning {@code this}
     */
    @ProvidesOwners 
    Set<String> getOwners();
}
