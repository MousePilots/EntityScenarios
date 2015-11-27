/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.abst;

import java.io.Serializable;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public abstract class AbstractIdentifiableUpdate<I extends Serializable, V extends Serializable> extends AbstractIdentifiableVersionedChange<I, V>
{

   private int attributeOrdinal;
   private AttributeES attribute;

   public AbstractIdentifiableUpdate()
   {
      super();
   }

   public AbstractIdentifiableUpdate(AttributeES attribute, I id, V version)
   {
      super((IdentifiableTypeES)attribute.getDeclaringType(), id, version);
      this.attributeOrdinal = attribute.getOrdinal();
      this.attribute = attribute;
   }

   public final AttributeES getAttribute()
   {
      return attribute;
   }

   @Override
   public final CRUD operation()
   {
      return CRUD.UPDATE;
   }
}