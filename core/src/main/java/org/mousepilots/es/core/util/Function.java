/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author geenenju
 * @param <D> domain-type
 * @param <R> range-type
 */
public interface Function<D, R> {
    /**
     * Applies a given function to all elements in the specified domain, returning a list of the results.
     * Results are ordered according to the iterator of the {@code domain}
     * @param <D>
     * @param <R>
     * @param domain
     * @param f
     * @return the results in {@code domain-}iterator order
     */
    public static <D, R> List<R> apply(Collection<D> domain, Function<D, R> f)
    {
        final List<R> retval = new ArrayList<>(domain.size());
        for (D d : domain) {
            retval.add(f.apply(d));
        }
        return Collections.unmodifiableList(retval);
    }
    
    /**
     * Applies a given function to all elements in the specified domain, returning a list of the results.
     * @param <D>
     * @param <R>
     * @param domain
     * @param f
     * @return the results in {@code domain-}iterator order
     */
    public static <D, R> List<R> apply(D[] domain, Function<D, R> f)
    {
        return apply(Arrays.asList(domain),f);
    }
    
    /**
     * Applies {@code this} to the specified {@code argument}
     * @param argument
     * @return the result of the application
     */
    R apply(D argument);
}
