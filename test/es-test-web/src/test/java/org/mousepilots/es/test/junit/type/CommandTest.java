/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.junit.type;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.test.domain.entities.Manager;
import org.mousepilots.es.test.domain.entities.ManagerAccount;
import org.mousepilots.es.test.domain.entities.ManagerAccount_ES;
import org.mousepilots.es.test.domain.entities.Manager_ES;
import org.mousepilots.es.test.shared.AbstractTest;

/**
 *
 * @author geenenju
 */
public class CommandTest extends AbstractTest{
    
    @Test
    public void testCreate(){
        EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();
        final EntityManagerES entityManager = entityManagerFactory.createEntityManager();
        final Manager manager = entityManager.create(Manager_ES.__TYPE);
        final ManagerAccount account = entityManager.create(ManagerAccount_ES.__TYPE);
        manager.setAccount(account);
        final List<Command> commands = entityManager.getTransaction().getCommands();
        Assert.assertTrue(commands.size()==3);
        
    }
    
}
