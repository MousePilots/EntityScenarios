package org.mousepilots.es.change;

import java.util.List;

/**
 * @author Roy Cleven
 */
public interface HasAdditions<T>
{
   /**
    * @return the additions to the collection
    */
   List<T> getAdditions();
}
