package org.mousepilots.es.core.change.abst;

import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public abstract class AbstractChange implements Change
{

   protected AbstractChange(){}

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
   public final ManagedTypeES getType()
   {
      return (ManagedTypeES) AbstractMetamodelES.getInstance().getType(typeOrdinal);
   }
   
    @Override
    public boolean equals(Object obj)
    {
        return this==obj;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + getClass().getCanonicalName().hashCode();
        hash = 29 * hash + this.typeOrdinal;
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[type=" + getType() + "]";
    }
    
    
    
    
   
   
}