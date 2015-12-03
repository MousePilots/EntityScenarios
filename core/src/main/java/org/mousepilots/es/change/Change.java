/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import java.io.Serializable;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.TypeES;

/**
 *
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public interface Change extends Serializable
{
   /**
    * @return the changed {@link DTO}'s {@link Type}
    */
   TypeES getType();

   /**
    * @return the operation which changed the {@link DTO}
    */
   CRUD operation();

   /**
    * Visitor patterns {@code accept}: accepts a {@link ChangeVisitor}.
    * 
    * @param changeHandler
    */
   void accept(ChangeVisitor changeHandler);

   /**
    * @param crud
    * @return whether or not {@code this} was caused by a {@code crud}
    */
   boolean is(CRUD crud);
   
   /**
    * Gets the {@link DtoType} which is used for the change, which determines the type of values.
    * @return the type of values which are eing used in {@code this}
    */
   DtoType getDtoType();
}