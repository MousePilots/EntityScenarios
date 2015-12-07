package org.mousepilots.es.core.change;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <ID> the {@link Id}-type
 */
public interface HasId<ID extends Serializable>
{
   /**
    * @return the Id value
    */
   ID getId();
}