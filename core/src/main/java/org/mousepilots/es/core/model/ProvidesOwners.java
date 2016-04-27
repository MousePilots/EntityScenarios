/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * Marker for a zero-arg method with return type {@link Set}{@code <}{@link String}{@code >} with at least protected visibility
 * which return the usernames owning an instance of a {@link ManagedTypeES#getJavaType()}. You may use your own annotation, as long as it simple
 * name equals "{@link ProvidesOwners}".
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidesOwners {
    
}
