package org.mousepilots.es.change.abst;

import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.change.Change;
import org.mousepilots.es.model.TypeES;

/**
 * @author Roy Cleven
 */
public abstract class AbstractChange implements Change
{

   protected AbstractChange()
   {
   }

   private int typeOrdinal;

   protected AbstractChange(TypeES type)
   {
      this.typeOrdinal = type.getOrdinal();
   }

   @Override
   public final boolean is(CRUD crud)
   {
      return operation() == crud;
   }

   @Override
   public final TypeES getType()
   {
      throw new UnsupportedOperationException();
   }
}