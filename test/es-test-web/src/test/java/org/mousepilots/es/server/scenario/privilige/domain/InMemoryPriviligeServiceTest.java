/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige.domain;

import java.util.Set;
import javax.persistence.metamodel.ManagedType;
import org.junit.Test;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.test.domain.MetamodelImpl;
import org.mousepilots.es.test.domain.entities.Manager_ES;

/**
 *
 * @author ap34wv
 */
public class InMemoryPriviligeServiceTest {
    
    static {
        MetamodelImpl.init();
    }
    
    @Test
    public void grantAllTypes(){
        for(ManagedType managedType : AbstractMetamodelES.getInstance().getManagedTypes()){
            final ManagedTypeES managedTypeES = (ManagedTypeES) managedType;
            final Set<AttributeES> attributes = managedTypeES.getAttributes();
            InMemoryPriviligeService.inScenario("scenario1").grant(CRUD.READ).on(managedTypeES,attributes).ok();
        }
    }
    
    @Test
    public void testCreateReadUpdate(){
        InMemoryPriviligeService.inScenario("scenario1").grant(CRUD.CREATE).on(Manager_ES.__TYPE,Manager_ES.account,Manager_ES.addresses).ok();
        InMemoryPriviligeService.inScenario("scenario1").grant(CRUD.READ).on(Manager_ES.__TYPE,Manager_ES.account,Manager_ES.addresses).ok();
        InMemoryPriviligeService.inScenario("scenario1").grant(CRUD.UPDATE).on(Manager_ES.__TYPE,Manager_ES.account,Manager_ES.addresses).ok();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMalformedDelete(){
        InMemoryPriviligeService.inScenario("scenario1").grant(CRUD.DELETE).on(Manager_ES.__TYPE,Manager_ES.account,Manager_ES.addresses).ok();
    }
    
    
}
