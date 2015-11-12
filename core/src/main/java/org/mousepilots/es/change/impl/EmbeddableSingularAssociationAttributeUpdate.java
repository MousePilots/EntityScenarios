package org.mousepilots.es.change.impl;

import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public final class EmbeddableSingularAssociationAttributeUpdate extends AbstractNonIdentifiableUpdate
{
   protected EmbeddableSingularAssociationAttributeUpdate()
   {
      super();
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}