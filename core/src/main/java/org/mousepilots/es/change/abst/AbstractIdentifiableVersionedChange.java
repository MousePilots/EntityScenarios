/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change.abst;

import java.io.Serializable;
import org.mousepilots.es.change.HasVersion;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Roy Cleven
 * @param <I>
 * @param <V>
 */
public abstract class AbstractIdentifiableVersionedChange<I extends Serializable, V extends Serializable> extends AbstractIdentifiableChange<I> implements HasVersion<V>
{

   public AbstractIdentifiableVersionedChange()
   {
      super();
   }

   public AbstractIdentifiableVersionedChange(IdentifiableTypeES type, I id, V version)
   {
      super(type, id);
      //TODO FIX
//      Attribute va = type.getVersion(type.get));
   }

   @Override
   public final V getVersion()
   {
      return null;
   }

}