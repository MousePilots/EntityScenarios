package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;


/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public class IdentifiableSingularAssociationAttributeUpdate<I extends Serializable, V extends Serializable> extends AbstractIdentifiableUpdate<I, V>
{

   protected IdentifiableSingularAssociationAttributeUpdate()
   {
      super();
   }

   @Override
   public final void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}