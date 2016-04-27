/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.util.tuples;

/**
 *
 * @author ap34wv
 */
public class Trio<T0,T1,T2> extends Duo<T0,T1>{
    
    private final T2 v2;

    public Trio(T0 v0, T1 v1, T2 v2) {
        super(v0, v1);
        this.v2 = v2;
    }

    public T2 getV2() {
        return v2;
    }
}
