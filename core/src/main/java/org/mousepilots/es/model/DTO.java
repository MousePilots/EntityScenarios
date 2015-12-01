/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.Change;

/**
 *
 * @author clevenro
 */
public interface Dto {

    IdentifiableTypeES getType();

    AttributeES getId();

    List<Change> getChanges();

    Class<?> getOriginalClass();

    /**
     * @param <S>
     * @return {@code this}'s {@link Id}-{@link AttributeES}'s value for an
     * identifiable type, otherwise {@code null};
     */
    <S extends Serializable> S getIdValue();

    /**
     * @param <S>
     * @return {@code this}'s {@link Version}-{@link AttributeES}'s value for a
     * versioned type, otherwise {@code null};
     */
    <S extends Serializable> S getVersionValue();
}
