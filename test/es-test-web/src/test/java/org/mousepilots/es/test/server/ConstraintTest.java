/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import org.mousepilots.es.test.constraints.ConstraintExceptionLogger;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.test.domain.embeddables.Address;
import org.mousepilots.es.test.domain.embeddables.Address_ES;
import org.mousepilots.es.test.domain.entities.Manager;
import org.mousepilots.es.test.domain.entities.ManagerAccount;
import org.mousepilots.es.test.domain.entities.ManagerAccount_ES;
import org.mousepilots.es.test.domain.entities.Manager_ES;
import org.mousepilots.es.test.server.domain.mmx.JPA;
import org.mousepilots.es.test.shared.AbstractTest;

/**
 *
 * @author bhofsted
 */
public class ConstraintTest extends AbstractTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public List<Command> commands;

    public ConstraintTest() {
        super(ConstraintTest.class);
    }

    public void testSucces() {
        final EntityManagerES entityManagerES = this.createEntityManager();
        final Manager manager = entityManagerES.create(Manager_ES.__TYPE);
        final ManagerAccount account = entityManagerES.create(ManagerAccount_ES.__TYPE);
        final Address address = entityManagerES.create(Address_ES.__TYPE);
        address.setZipCode("1234AB");
        manager.setAccount(account);
        commands = entityManagerES.getTransaction().getCommands();
        TestScenarioRequest request = new TestScenarioRequest();
        request.setCommands(commands);
        ServerContext serverContext = new ServerContextImpl(JPA.createEntityManager());
        request.setServerContext(serverContext);
        ScenarioServiceBean scenarioServiceBean = new ScenarioServiceBean();
        scenarioServiceBean.submit(commands);
        final Set<ConstraintViolation<TestScenarioRequest>> exceptions = validator.validate(request, Default.class);
//        assertTrue(exceptions.isEmpty());
    }

    public void testFail() {
        final EntityManagerES entityManagerES = this.createEntityManager();
        final Manager manager1 = entityManagerES.create(Manager_ES.__TYPE), manager2 = entityManagerES.create(Manager_ES.__TYPE);
        final ManagerAccount account = entityManagerES.create(ManagerAccount_ES.__TYPE);
        final Address address = entityManagerES.create(Address_ES.__TYPE);
        address.setZipCode("1234A");
        manager1.setAccount(account);
        commands = entityManagerES.getTransaction().getCommands();
        TestScenarioRequest request = new TestScenarioRequest();
        request.setCommands(commands);
        ServerContext serverContext = ServerContextMocker.getServerContext();//serverContextMocker();//new ServerContextImpl(JPA.createEntityManager());
        request.setServerContext(serverContext);
        ScenarioServiceBean scenarioServiceBean = new ScenarioServiceBean();
        scenarioServiceBean.submit(commands);
        final Set<ConstraintViolation<TestScenarioRequest>> exceptions = validator.validate(request, Default.class);
        assertFalse(exceptions.isEmpty());

//        new ConstraintExceptionLogger().clearLog(); //uncomment for fresh log
        new ConstraintExceptionLogger().logger(exceptions);
    }
}