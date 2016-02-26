package org.mousepilots.es.core.change;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.model.IdentifiableTypeES;

/**
 * @author Roy Cleven
 */
public interface ExecutionSummary {
    
    /**
     * @return an unmodifiable {@link Map} of {@link IdentifiableTypeES} &rarr; an unmodifiable {@link Map} of clientId &rarr; persistentId
     */
    Map<IdentifiableTypeES,Map<Serializable,Serializable>> getClientIdToPersistetId();
    
    /**
     * @param <T>
     * @param javaType
     * @param operations
     * @return a list of instances of {@code T} on which one of the given {@code operations} was performed 
     */
    <T> List<T> get(Class<T> javaType, CRUD... operations);
}
