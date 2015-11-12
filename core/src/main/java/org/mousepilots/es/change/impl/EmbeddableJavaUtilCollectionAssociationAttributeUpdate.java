package org.mousepilots.es.change.impl;

import java.util.ArrayList;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;


/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <A>
 */
public final class EmbeddableJavaUtilCollectionAssociationAttributeUpdate<A> extends AbstractNonIdentifiableUpdate implements HasAdditions<A>, HasRemovals<A>
{


   private EmbeddableJavaUtilCollectionAssociationAttributeUpdate()
   {
      super();
   }


   @Override
   public List<A> getAdditions()
   {
      return null;
   }

   @Override
   public ArrayList<A> getRemovals()
   {
      return null;
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }
}