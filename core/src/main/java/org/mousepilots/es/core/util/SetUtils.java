/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.mousepilots.es.core.model.impl.Constructor;

/**
 *
 * @author ap34wv
 */
public class SetUtils {
    public static <T> Set<T> intersection(Set<T> s0, Set<T> s1){
        final Set<T> retval = new HashSet<>();
        retval.addAll(s0);
        retval.retainAll(s1);
        return retval;
    }
    
    public static <T> Set<T> join(Collection<Set<T>> sets){
        HashSet<T> retval = new HashSet<>();
        for(Set<T> s : sets){
            retval.addAll(s);
        }
        return retval;
    }

    
    public static <T> Set<T> join(Set<T>... sets){
        return join(Arrays.asList(sets));
    }
}
