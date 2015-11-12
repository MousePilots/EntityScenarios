package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableChange;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 */
public final class Create<I extends Serializable> extends AbstractIdentifiableChange<I>
{

   protected Create()
   {
      super();
   }

   /**
    * Constructor: create a new Create.
    * 
    * @param type type of the new object to create
    * @param id id of the new object to create
    */
   public Create(IdentifiableTypeES type, I id)
   {
      super(type, id);
   }

   @Override
   public CRUD operation()
   {
      return CRUD.CREATE;
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}