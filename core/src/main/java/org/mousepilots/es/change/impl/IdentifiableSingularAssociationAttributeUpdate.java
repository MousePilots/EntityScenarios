/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.HasValue;


/**
 * @author geenenju
 */
public abstract class IdentifiableSingularAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends AbstractIdentifiableUpdate<I, V>
{

   private HasValue oldValue;
   private HasValue newValue;

   protected IdentifiableSingularAssociationAttributeUpdate()
   {
      super();
   }

   public IdentifiableSingularAssociationAttributeUpdate(AttributeES attribute, I id, V version, A oldValue, A newValue)
   {
      super(attribute, id, version);
      this.oldValue = attribute.wrapForChange(oldValue);
      this.newValue = attribute.wrapForChange(newValue);
   }


   public A getOldValue()
   {
      return oldValue == null ? null : (A) oldValue.getValue();
   }

   public A getNewValue()
   {
      return newValue == null ? null : (A) newValue.getValue();
   }
}