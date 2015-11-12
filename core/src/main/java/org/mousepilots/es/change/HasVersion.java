/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import java.io.Serializable;

import javax.persistence.Version;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <V> the {@link Version}-type
 */
public interface HasVersion<V extends Serializable>
{
   /**
    * @return the version
    */
   V getVersion();
}