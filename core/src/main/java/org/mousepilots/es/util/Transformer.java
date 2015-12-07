package org.mousepilots.es.util;

import java.util.Collection;

/**
 * @author Roy Cleven
 */
public interface Transformer<T, C extends Collection<T>> {

    C transform(C collection);
    
}
