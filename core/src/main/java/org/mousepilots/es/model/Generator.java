package org.mousepilots.es.model;

import java.io.Serializable;

/**
 * @project EntityScenario
 * @author clevenro
 */
public interface Generator<T extends Serializable>{

    T generate();
    
    void reset();
}
