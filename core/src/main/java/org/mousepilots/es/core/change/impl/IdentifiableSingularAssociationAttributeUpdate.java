package org.mousepilots.es.core.change.impl;

import java.io.Serializable;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;


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

    public IdentifiableSingularAssociationAttributeUpdate(AttributeES attribute, 
            V version, HasValue id, IdentifiableTypeES type, DtoType dtoType,
            Serializable oldValue, Serializable newValue) {
        super(attribute, version, id, type, dtoType);
      this.oldValue = attribute.wrapForChange(oldValue, dtoType);
      this.newValue = attribute.wrapForChange(newValue, dtoType);
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