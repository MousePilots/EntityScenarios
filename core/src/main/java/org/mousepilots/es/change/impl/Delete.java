package org.mousepilots.es.change.impl;

import java.io.Serializable;

import javax.persistence.Version;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableVersionedChange;
import org.mousepilots.es.model.IdentifiableTypeES;


public final class Delete<I extends Serializable, V extends Serializable> extends AbstractIdentifiableVersionedChange<I, V>
{

   protected Delete()
   {
      super();
   }

   /**
    * Constructor: create a new Delete.
    * 
    * @param type type of the new object to create
    * @param id id of the new object to create
    * @param version the source {@link Version}, may be {@code null}
    */
   public Delete(IdentifiableTypeES type, I id, V version)
   {
      super(type, id, version);
   }

   @Override
   public CRUD operation()
   {
      return CRUD.DELETE;
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}