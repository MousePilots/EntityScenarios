package org.mousepilots.es.server;

import java.io.Serializable;
import org.mousepilots.es.model.TypeES;


public class TypeAndId
{
   private final TypeES type;
   private final Serializable id;

   /**
    * Constructor: create a new TypeAndId.
    * 
    * @param type
    * @param id
    */
   public TypeAndId(TypeES type, Serializable id)
   {
      this.type = type;
      this.id = id;
   }

   /**
    * Get the type.
    * 
    * @return Returns the type as a Type.
    */
   public TypeES getType()
   {
      return type;
   }

   /**
    * Get the id.
    * 
    * @return Returns the id as a Serializable.
    */
   public Serializable getId()
   {
      return id;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      return result;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      TypeAndId other = (TypeAndId) obj;
      if (id == null)
      {
         if (other.id != null)
            return false;
      }
      else if (!id.equals(other.id))
         return false;
      if (type == null)
      {
         if (other.type != null)
            return false;
      }
      else if (!type.equals(other.type))
         return false;
      return true;
   }

}
