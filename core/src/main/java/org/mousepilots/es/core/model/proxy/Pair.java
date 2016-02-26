/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

/**
 *
 * @author geenenju
 */
public class Pair<X,Y> {
     
     private X first;
     
     private Y second;

     public Pair(X first, Y second) {
          this.first = first;
          this.second = second;
     }

     public X getFirst() {
          return first;
     }

     public Y getSecond() {
          return second;
     }
}
