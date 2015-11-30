/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.Dto;
import org.mousepilots.es.model.HasValue;



/**
 * @author geenenju
 */
public final class EmbeddableSingularAssociationAttributeUpdate<A extends Serializable> extends AbstractNonIdentifiableUpdate
{

   private HasValue oldValue;
   private HasValue newValue;

   protected EmbeddableSingularAssociationAttributeUpdate()
   {
      super();
   }

   public EmbeddableSingularAssociationAttributeUpdate(Dto container, AttributeES containerAttribute, 
           Dto updated, AttributeES updatedAttribute, A oldValue, A newValue)
   {
      super(container, containerAttribute, updated, updatedAttribute);
      this.oldValue = containerAttribute.wrapForChange(oldValue);
      this.newValue = containerAttribute.wrapForChange(newValue);
   }

   public A getOldValue()
   {
      return oldValue == null ? null : (A)oldValue.getValue();
   }

   public A getNewValue()
   {
      return newValue == null ? null : (A) newValue.getValue();
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}