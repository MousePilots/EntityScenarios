/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author Roy Cleven
 * @param <ID> the {@link Id}-type
 */
public interface HasId<ID extends Serializable>
{
   /**
    * @return the Id value
    */
   ID getId();
}
