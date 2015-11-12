package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.Wrapper;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableSingularBasicAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends AbstractIdentifiableUpdate<I, V>
{

   private Wrapper oldValue;
   private Wrapper newValue;

   protected IdentifiableSingularBasicAttributeUpdate()
   {
      super();
   }

   public IdentifiableSingularBasicAttributeUpdate(AttributeES attribute, I id, V version, A oldValue, A newValue)
   {
      super(attribute, id, version);
//      this.oldValue = attribute.wrap(oldValue);
//      this.newValue = attribute.wrap(newValue);
   }

   public A getOldValue()
   {
      return oldValue == null ? null : (A) oldValue.unwrap();
   }

   public A getNewValue()
   {
      return newValue == null ? null : (A) newValue.unwrap();
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}