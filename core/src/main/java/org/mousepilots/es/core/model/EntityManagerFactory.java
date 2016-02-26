/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

import org.mousepilots.es.core.model.impl.AbstractMetamodelES;



/**
 *
 * @author jgeenen
 */
public interface EntityManagerFactory extends javax.persistence.EntityManagerFactory{

    @Override
    public default void close() {
    
    }

    @Override
    public default EntityManager createEntityManager() {
        throw new UnsupportedOperationException("TODO");
        //return new EntityManagerESImpl(getMetamodel());
    }

    @Override
    public default MetamodelES getMetamodel() {
        return AbstractMetamodelES.getInstance();
    }
    
    
    
    
    
    
    
}
