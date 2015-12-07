package org.mousepilots.es.core.change.abst;

import java.util.Objects;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public abstract class AbstractChange implements Change
{

   protected AbstractChange()
   {
   }

   private int typeOrdinal;
   private DtoType dtoType;

   protected AbstractChange(TypeES type, DtoType dtoType)
   {
      this.typeOrdinal = type.getOrdinal();
      this.dtoType = dtoType;
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
   
   @Override
   public final DtoType getDtoType(){
       return dtoType;
   }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + getClass().getCanonicalName().hashCode();
        hash = 29 * hash + this.typeOrdinal;
        hash = 29 * hash + Objects.hashCode(this.dtoType);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "[type=" + getType() + ", dtoType=" + getDtoType() + "]";
    }
    
    
    
    
   
   
}