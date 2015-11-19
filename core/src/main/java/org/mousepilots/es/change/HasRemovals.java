package org.mousepilots.es.change;

import java.util.List;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <A>
 */
public interface HasRemovals<A>
{
   /**
    * @return the values removed
    */
   List<A> getRemovals();
}