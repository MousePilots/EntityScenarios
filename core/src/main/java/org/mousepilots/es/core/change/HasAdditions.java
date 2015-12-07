package org.mousepilots.es.core.change;

import java.util.List;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <T>
 */
public interface HasAdditions<T>
{
   /**
    * @return the additions to the collection
    */
   List<T> getAdditions();
}