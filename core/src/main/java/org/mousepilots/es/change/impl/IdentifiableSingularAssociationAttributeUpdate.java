package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;


/**
 * @author geenenju
 * @param <I>
 * @param <V>
 * @param <A>
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
      this.oldValue = attribute.wrapForChange(oldValue, DtoType.MANAGED_CLASS);
      this.newValue = attribute.wrapForChange(newValue, DtoType.MANAGED_CLASS);
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