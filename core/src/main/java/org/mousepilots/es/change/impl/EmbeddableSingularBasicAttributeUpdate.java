/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.impl;

import java.io.Serializable;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.Dto;


/**
 * @author geenenju
 */
public final class EmbeddableSingularBasicAttributeUpdate<A extends Serializable> extends AbstractNonIdentifiableUpdate
{

   private HasValue oldValue;
   private HasValue newValue;

   protected EmbeddableSingularBasicAttributeUpdate()
   {
      super();
   }

   public EmbeddableSingularBasicAttributeUpdate(Dto container, AttributeES containerAttribute, Dto updated, AttributeES updatedAttribute, A oldValue, A newValue)
   {
      super(container, containerAttribute, updated, updatedAttribute);
      this.oldValue = updatedAttribute.wrapForChange(oldValue);
      this.newValue = updatedAttribute.wrapForChange(newValue);
   }

   public A getOldValue()
   {
      return oldValue == null ? null : (A) oldValue.getValue();
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
