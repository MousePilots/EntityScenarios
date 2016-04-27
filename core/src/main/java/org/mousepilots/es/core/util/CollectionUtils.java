/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

import java.util.Collection;

/**
 *
 * @author jgeenen
 */
public class CollectionUtils {

    public static <T, C extends Collection<T>> void ensureAdded(C collection, T t) throws IllegalStateException {
        if (!collection.add(t)) {
            throw new IllegalStateException("duplicate entry of " + t);
        }
    }

    public static <T> boolean containsAny(Collection<T> collection, Collection<T> referenceElements) {
        for (T referenceElement : referenceElements) {
            if (collection.contains(referenceElement)) {
                return true;
            }
        }
        return false;
    }
    
}
