/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util.tuples;

/**
 *
 * @author ap34wv
 * @param <T0>
 * @param <T1>
 */
public class Duo<T0,T1>{

    private final T0 v0;
    
    private final T1 v1;

    public Duo(T0 v0, T1 v1) {
        this.v0 = v0;
        this.v1 = v1;
    }

    public T0 getV0() {
        return v0;
    }

    public T1 getV1() {
        return v1;
    }
}
