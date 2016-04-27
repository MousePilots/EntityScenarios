/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.util.logging.Logger;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.test.domain.MetamodelImpl;

/**
 *
 * @author geenenju
 */
public class AbstractTest{
    
    static {
        MetamodelImpl.init();
    }

    private final EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();

    protected final EntityManagerES createEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    
    protected final Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    protected final AbstractMetamodelES getMetaModel() {
        return MetamodelImpl.INSTANCE;
    }

}
