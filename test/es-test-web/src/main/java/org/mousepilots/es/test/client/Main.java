package org.mousepilots.es.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import java.util.function.Predicate;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.test.domain.MetamodelImpl;
import org.mousepilots.es.test.domain.entities.Manager;
import org.mousepilots.es.test.domain.entities.ManagerAccount;
import org.mousepilots.es.test.domain.entities.ManagerAccount_ES;
import org.mousepilots.es.test.domain.entities.Manager_ES;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author geenenju
 */
public class Main implements EntryPoint {

    @Override
    public void onModuleLoad() {
        MetamodelImpl.init();
        EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();
        final EntityManagerES entityManager = entityManagerFactory.createEntityManager();
        final Manager manager = entityManager.create(Manager_ES.__TYPE);
        final ManagerAccount account = entityManager.create(ManagerAccount_ES.__TYPE);
        manager.setAccount(account);
        final List<Command> commands = entityManager.getTransaction().getCommands();
        ScenarioServiceAsync.Util.getInstance().submit(commands, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage() );
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("success");
            }
        });

        Predicate p = o -> true;
        
        Window.alert(p.test(null) + "");
    }
}
