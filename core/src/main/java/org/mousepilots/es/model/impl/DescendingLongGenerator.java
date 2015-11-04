package org.mousepilots.es.model.impl;

import org.mousepilots.es.model.Generator;

/**
 * Class that implements the {@link Generator} interface.
 * @author Roy Cleven
 * @version 1.0, 3-11-2015
 * @see Generator
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