package org.mousepilots.es.change.impl;

import java.io.Serializable;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;


/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <A>
 */
public final class EmbeddableSingularBasicAttributeUpdate<A extends Serializable> extends AbstractNonIdentifiableUpdate
{
   protected EmbeddableSingularBasicAttributeUpdate()
   {
      super();
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}