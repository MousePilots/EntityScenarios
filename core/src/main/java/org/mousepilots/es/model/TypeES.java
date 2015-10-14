/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.metamodel.Type;

/**
 *
 * @author clevenro
 * @param <T>
 */
public interface TypeES<T> extends Type<T>{
    
    String getJavaClassName();
    
    String getName();
    
    int getOrdinal();
    
    boolean isInstantiable();
    
    Serializable createInstance();
    
    Class getMetamodelClass();
    
    Collection<Type> getSuperTypes();
    
    Collection<Type> getSubTypes();
}
