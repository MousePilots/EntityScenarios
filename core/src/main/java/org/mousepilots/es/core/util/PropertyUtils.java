/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util;

import java.util.Objects;

/**
 *
 * @author jgeenen
 */
public class PropertyUtils {
    
    /**
     * Tests whether or not {code t1,t2} are equal with respect to the {@code getters}
     * @param <T>
     * @param t1
     * @param t2
     * @param getters
     * @return
     */
    public static <T> boolean equals(T t1, T t2, Function<T,?>... getters){
        for(Function<T,?> getter : getters){
            if(!Objects.equals(getter.apply(t1), getter.apply(t2))){
                return false;
            }
        }
        return true;
    }
    
    public static <T,R extends RuntimeException> void assertNotEquals(T t1, T t2, Producer<R> runtimeExceptionProducer, Function<T,?>... getters) throws R{
        if(equals(t1, t2, getters)){
            throw runtimeExceptionProducer.produce();
        }
    }
    
}
