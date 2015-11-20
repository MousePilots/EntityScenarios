package org.mousepilots.es.change.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.HasAdditions;
import org.mousepilots.es.change.HasRemovals;
import org.mousepilots.es.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.HasValue;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 * @param <A>
 */
public final class IdentifiableJavaUtilCollectionBasicAttributeUpdate<I extends Serializable, V extends Serializable, A extends Serializable> extends
      AbstractIdentifiableUpdate<I, V> implements HasAdditions<A>, HasRemovals<A>
{
   private ArrayList<HasValue> additions = new ArrayList<>();
   private ArrayList<HasValue> removals = new ArrayList<>();

   private IdentifiableJavaUtilCollectionBasicAttributeUpdate()
   {
      super();
   }

   public IdentifiableJavaUtilCollectionBasicAttributeUpdate(final AttributeES attribute, I id, V version, Collection<A> additions, Collection<A> removals)
   {
      super(attribute, id, version);
//      ValueWrappers.wrap(attribute, additions, this.additions);
//      ValueWrappers.wrap(attribute, removals, this.removals);
   }

   @Override
   public void accept(ChangeVisitor changeHandler)
   {
      changeHandler.visit(this);
   }

   @Override
   public List<A> getAdditions()
   {
      return null;//ValueWrappers.unWrap(this.additions, new ArrayList<A>());
   }

   @Override
   public List<A> getRemovals()
   {
      return null;//ValueWrappers.unWrap(this.removals, new ArrayList<A>());
   }
}