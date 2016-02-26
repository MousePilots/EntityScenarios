/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl;

import java.io.Serializable;
import org.mousepilots.es.core.model.TypeES;

/**
 *
 * @author geenenju
 * @param <T>
 */
public abstract class HasType<T extends TypeES> implements Serializable{
     
     private int typeOrdinal;

     protected HasType(){}
     
     protected HasType(int typeOrdinal) {
          this.typeOrdinal = typeOrdinal;
     }
     
     protected HasType(T type){
          this.typeOrdinal = type.getOrdinal();
     }

     public T getType() {
          return (T) AbstractMetamodelES.getInstance().getType(typeOrdinal);
     }
}
