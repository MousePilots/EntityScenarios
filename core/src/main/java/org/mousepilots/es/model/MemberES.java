/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.lang.reflect.Member;

/**
 *
 * @author ernsteni
 */
public interface MemberES extends Member {
    <T> T get(Object instance);
    void set(Object instance, Object value);
    //Setter (set, add, remove en put) interceptie op implementatie niveau.
}
