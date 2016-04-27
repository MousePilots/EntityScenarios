/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.junit.type;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.command.DeleteEntity;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityTransaction;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.test.shared.AbstractTest;

/**
 *
 * @author geenenju
 */
public class CommandTest extends AbstractTest{
    
    
    @Test
    public void testCreateAndDeleteEntityWithGeneratedId(){
        final long startTime = System.currentTimeMillis();
        final Set<EntityTypeESImpl<?>> entityTypes = (Set) getMetaModel().getEntities();
        int typesTested=0;
        for(EntityTypeESImpl entityType : entityTypes){
            getLogger().log(Level.FINE, "testing create of {0}", entityType);
            if(entityType.isInstantiable()){
                if(entityType.getId().isGenerated()){
                    final EntityManagerES entityManager = createEntityManager();
                    typesTested++;
                    final Proxy creation = (Proxy) entityManager.create(entityType);
                    Assert.assertNotNull("failed to create entity of type " + entityType, creation);
                    final Object id = entityType.getId().getJavaMember().get(creation);
                    final Class javaType = entityType.getJavaType();
                    Assert.assertEquals(creation, entityManager.find(javaType, id));
                    final EntityTransaction transaction = entityManager.getTransaction();
                    List<Command> commands = transaction.getCommands();
                    Assert.assertEquals(1, commands.size());
                    final Command create = commands.get(0);
                    Assert.assertTrue("expected an instance of " + CreateEntity.class.toString(), create instanceof CreateEntity);
                    Assert.assertTrue("expected " + entityType + " instead of " + create.getType(), create.getType()==entityType);
                    Assert.assertTrue(creation.__getProxyAspect().isCreated());
                    entityManager.remove(creation);
                    Assert.assertTrue(creation.__getProxyAspect().isDeleted());
                    Assert.assertNull(create + " must not be found after removal", entityManager.find(javaType, id));
                    
                    commands = transaction.getCommands();
                    Assert.assertEquals(2, commands.size());
                    final Command delete = commands.get(1);
                    Assert.assertTrue("expected an instance of " + DeleteEntity.class.toString(), delete instanceof DeleteEntity);
                    Assert.assertTrue("expected " + entityType + " instead of " + delete.getType(), delete.getType()==entityType);
                    
                    
                    //undo delete
                    transaction.undo();
                    Assert.assertEquals(1, transaction.getCommands().size());
                    Assert.assertFalse(creation.__getProxyAspect().isDeleted());
                    Assert.assertEquals(create + " must be found after undoing removal", creation, entityManager.find(javaType, id));
                    
                    //undo create
                    transaction.undo();
                    Assert.assertEquals(0, transaction.getCommands().size());
                    Assert.assertFalse(creation.__getProxyAspect().isDeleted());
                    Assert.assertFalse(creation.__getProxyAspect().isCreated());
                    Assert.assertNull(create + " must not be found after undoing creation", entityManager.find(javaType, id));
                }
            }
        }
        getLogger().log(Level.INFO, "tested create on {0} entity types in {1} ms", new Object[]{typesTested, System.currentTimeMillis()-startTime});
//        
//        final Manager manager = entityManager.create(Manager_ES.__TYPE);
//        final ManagerAccount account = entityManager.create(ManagerAccount_ES.__TYPE);
//        manager.setAccount(account);
//        Assert.assertTrue(entityManager.getTransaction().getCommands().size()==3);
//        entityManager.getTransaction().undo();
//        Assert.assertTrue(entityManager.getTransaction().getCommands().size()==2);
//        Assert.assertNull(manager.getAccount());
        
    }
    
}
