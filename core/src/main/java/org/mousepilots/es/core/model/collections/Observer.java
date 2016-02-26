/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.collections;

import java.io.Serializable;

/**
 * Listens for changes to a subject
 * @author geenenju
 * @param <T> the subject's type
 */
public interface Observer<T> extends Serializable {

    /**
     * invoked before {@code subject.clear()}
     * @param subject
     */
    void onClear(T subject);

    /**
     * invoked before {@code subject.remove(o)}
     * @param subject
     * @param o 
     */
    void onRemove(T subject, Object o);
}
