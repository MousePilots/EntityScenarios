/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import java.util.List;

/**
 * @author Roy Cleven
 * @param <A>
 */
public interface HasRemovals<A>
{
   /**
    * @return the values removed
    */
   List<A> getRemovals();
}