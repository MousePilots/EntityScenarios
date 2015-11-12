package org.mousepilots.es.change.abst;

import org.mousepilots.es.change.CRUD;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public abstract class AbstractNonIdentifiableUpdate extends AbstractChange
{

   protected AbstractNonIdentifiableUpdate()
   {

   }

   @Override
   public final CRUD operation()
   {
      return CRUD.UPDATE;
   }
}
