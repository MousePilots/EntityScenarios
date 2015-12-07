package org.mousepilots.es.core.model;

/**
 *
 * @author clevenro
 */
public enum DtoType {
    //DTO , Classes self , subclasses for the actual classes
    SINGLE, MANAGED_CLASS, MANAGED_SUB_CLASS;

    public void assertSupported() throws UnsupportedOperationException{
        if (this != MANAGED_CLASS) {
            throw new UnsupportedOperationException(this + " is not yet supported");
        }
    }
}