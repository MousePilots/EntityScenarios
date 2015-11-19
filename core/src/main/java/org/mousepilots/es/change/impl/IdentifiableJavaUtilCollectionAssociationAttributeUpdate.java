package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableJavaUtilCollectionAssociationAttributeUpdate<I extends Serializable, V extends Serializable, A> extends AbstractIdentifiableUpdate<I, V> implements HasAdditions<A>,
      HasRemovals<A>
{
   private IdentifiableJavaUtilCollectionAssociationAttributeUpdate()
   {
      super();
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }

   @Override
   public List<A> getAdditions()
   {
      return null;
   }

   @Override
   public List<A> getRemovals()
   {
      return null;
   }
}