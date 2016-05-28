/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.util.Set;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.test.domain.MetamodelImpl;

/**
 *
 * @author geenenju
 */
public abstract class AbstractTest extends TestCase{
    
    static {
        MetamodelImpl.init();
    }

    protected AbstractTest(Class<? extends AbstractTest> clazz){
        super(clazz.getSimpleName());
    }
    
    private final EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();
    
    protected final EntityManagerImpl createEntityManager(){
        return (EntityManagerImpl) entityManagerFactory.createEntityManager();
    }
    
    protected final Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    protected final AbstractMetamodelES getMetaModel() {
        return MetamodelImpl.INSTANCE;
    }
    
    protected Set<ManagedTypeESImpl> getManagedTypes(){
        return (Set) getMetaModel().getManagedTypes();
    }

}
