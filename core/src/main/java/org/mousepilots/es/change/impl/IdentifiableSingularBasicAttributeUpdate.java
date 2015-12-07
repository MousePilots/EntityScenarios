package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableSingularBasicAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends AbstractIdentifiableUpdate<I, V>
{

   private HasValue oldValue;
   private HasValue newValue;

   protected IdentifiableSingularBasicAttributeUpdate()
   {
      super();
   }

    public IdentifiableSingularBasicAttributeUpdate(AttributeES attribute, V version, HasValue id, IdentifiableTypeES type, DtoType dtoType) {
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

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}