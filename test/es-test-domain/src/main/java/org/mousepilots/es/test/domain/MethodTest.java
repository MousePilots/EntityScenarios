/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.domain;

import java.lang.reflect.Method;
import org.mousepilots.es.test.domain.entities.EmbeddableMap;

/**
 *
 * @author jgeenen
 */
public class MethodTest {
    
    public static void main (String[] args){
        for(Method m : EmbeddableMap.class.getMethods()){
            final String genericString = m.toGenericString();
            System.out.println(genericString.replace(" " + m.getDeclaringClass().getName() + ".", " "));
        }
    }
    
}
