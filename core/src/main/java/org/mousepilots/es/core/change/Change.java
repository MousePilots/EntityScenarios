/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change;

import java.io.Serializable;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.TypeES;

/**
 *
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public interface Change extends Serializable {

     /**
      * @return the corresponding {@link Type}
      */
     TypeES getType();

     /**
      * @return the operation which changed the {@link DTO}
      */
     CRUD operation();

     /**
      * @param crud
      * @return {@code operation()==crud}
      */
     default boolean is(CRUD crud) {
          return crud == operation();
     }

}
