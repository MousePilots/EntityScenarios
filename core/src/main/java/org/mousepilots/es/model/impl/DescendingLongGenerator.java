package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.Generator;

/**
 * @author Roy Cleven
 */
public class DescendingLongGenerator implements Generator<Long>
{
   private long longGenerator = Long.MAX_VALUE;

   @Override
   public synchronized Long generate()
   {
      return longGenerator--;
   }

   @Override
   public synchronized void reset()
   {
      longGenerator = Long.MAX_VALUE;
   }
}