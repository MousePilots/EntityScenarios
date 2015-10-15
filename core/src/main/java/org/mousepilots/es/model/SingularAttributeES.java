package org.mousepilots.es.model;

/**
 * @project EntityScenario
 * @author clevenro
 */
public interface SingularAttributeES<X, T> extends AttributeES<X, T>, BindableES<T>{

    boolean isGenerated();
    
    Generator getGenerator();
}
