/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige.domain;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.test.domain.entities.Manager_ES;
import org.mousepilots.es.test.server.ExceptionUtils;
import org.mousepilots.es.test.server.ServerContextImpl;
import org.mousepilots.es.test.shared.AbstractTest;

/**
 *
 * @author ap34wv
 */
public class InMemoryPriviligeServiceTest extends AbstractTest{
    
    private static final String SCENARIO = "scenario1";
    
    private final ServerContext serverContext = new ServerContextImpl(null);

    public InMemoryPriviligeServiceTest() {
        super(InMemoryPriviligeServiceTest.class);
    }
    
    public void testGrantReadAll(){
        for(ManagedType managedType : getMetaModel().getManagedTypes()){
            final ManagedTypeES managedTypeES = (ManagedTypeES) managedType;
            final Set<AttributeES> attributes = managedTypeES.getAttributes();
            InMemoryPriviligeService.inScenario(SCENARIO).grant(CRUD.READ).on(managedTypeES,attributes).ok();
        }
        final Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> priviliges = InMemoryPriviligeService.getInstance().getPriviliges(SCENARIO, serverContext);
        
        for(ManagedType managedType : getMetaModel().getManagedTypes()){
            final Set<AttributeES> storedPriviliges = priviliges.get(CRUD.READ).get(managedType);
            assertTrue(
                    storedPriviliges.containsAll(managedType.getAttributes()) && managedType.getAttributes().containsAll(storedPriviliges)
            );
        }
    }
    
    public void testGrantDeleteAll(){
        for(ManagedType managedType : getMetaModel().getManagedTypes()){
            final ManagedTypeES managedTypeES = (ManagedTypeES) managedType;
            InMemoryPriviligeService.inScenario(SCENARIO).grant(CRUD.DELETE).on(managedTypeES).ok();
        }
        final Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> priviliges = InMemoryPriviligeService.getInstance().getPriviliges(SCENARIO, serverContext);
        
        final Map<ManagedTypeES, Set<AttributeES>> deletePriviliges = priviliges.get(CRUD.DELETE);
        for(ManagedType managedType : getMetaModel().getManagedTypes()){
            assertTrue(deletePriviliges.containsKey(managedType));
        }
    }
    
    
    public void testCreateReadUpdate(){
        final EnumSet<CRUD> operations = EnumSet.of(CRUD.CREATE, CRUD.READ,CRUD.UPDATE);
        final List<AttributeES> allowedAttributes = Arrays.asList(Manager_ES.account, Manager_ES.addresses);
        for(CRUD operation : operations){
            InMemoryPriviligeService.inScenario(SCENARIO).grant(operation).on(Manager_ES.__TYPE,allowedAttributes).ok();
        }
        final Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> priviliges = InMemoryPriviligeService.getInstance().getPriviliges(SCENARIO, serverContext);
        
        for(CRUD operation : operations){
            assertTrue(priviliges.get(operation).get(Manager_ES.__TYPE).containsAll(allowedAttributes));
        }
    }
    
    public void testMalformedDelete(){
        ExceptionUtils.assertThrows(
            () -> InMemoryPriviligeService.inScenario(SCENARIO).grant(CRUD.DELETE).on(Manager_ES.__TYPE,Manager_ES.account,Manager_ES.addresses).ok(), 
            IllegalArgumentException.class
        );
    }
    
    
}
