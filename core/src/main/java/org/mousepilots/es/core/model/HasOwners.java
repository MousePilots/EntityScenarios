/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import java.util.Set;

/**
 * Provides the usernames owning an instance of a {@link ManagedTypeES}'s java type.
 * @author jgeenen
 */
public interface HasOwners{
    
    public static final String ANNOTATION_NAME="ProvidesOwners";
    
    @ProvidesOwners
    Set<String> getOwners();
}
