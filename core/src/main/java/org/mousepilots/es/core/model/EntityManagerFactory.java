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
public interface EntityManagerFactory{

    
    EntityManagerES createEntityManager();

    public default MetamodelES getMetamodel() {
        return AbstractMetamodelES.getInstance();
    }
    
}
