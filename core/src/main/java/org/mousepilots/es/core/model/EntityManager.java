/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.metamodel.IdentifiableType;

/**
 *
 * @author jgeenen
 */
public interface EntityManager extends javax.persistence.EntityManager{

    /**
     * Creates an instance of a managed class. The {@code id}-value is required <i><em>iff</em></i> {@code E} is an {@link IdentifiableType} the {@link Id} of which is no {@link GeneratedValue}.
     * In all other cases the {@code id} is ignored.
     * @param <E> the managed type
     * @param type the managed type
     * @param id required <i><em>iff</em></i>: 
     *  <ul>
     *      <li>{@code E} is an {@link IdentifiableType}</li>
     *      <li> {@code E's} {@link Id}-attribute is no {@link GeneratedValue}</li>
     * </ul>
     * @return the newly created instance with {@code id} set if applicable
     */
    <E> E create(ManagedTypeES<E> type, Object id);

}
