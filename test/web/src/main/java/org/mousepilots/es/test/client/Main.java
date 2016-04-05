package org.mousepilots.es.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.impl.Getter;
import org.mousepilots.es.core.model.impl.PropertyMember;
import org.mousepilots.es.core.model.impl.Setter;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ScenarioGraph;
import org.mousepilots.es.test.domain.entities.Account;
import org.mousepilots.es.test.domain.entities.Account_ES;
import org.mousepilots.es.test.domain.entities.Account_ES_Proxy;
import org.mousepilots.es.test.shared.Persistency;


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
        EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();
        org.mousepilots.es.test.domain.MetamodelImpl.init();
        Proxy proxy = new Account_ES_Proxy();
        Command command = new CreateEntity((EntityManagerImpl) entityManagerFactory.createEntityManager(),Account_ES.__TYPE,null);
        GWT.create(Persistency.class);
        final SingularAttribute<Account, String> description = Account_ES.description;

        MemberES member = new PropertyMember(
                Account.class, "description",
                (Getter<Account, String>) Account::getDescription,
                (Setter<Account, String>) Account::setDescription,
                0x00000001
        );

        ScenarioGraph s = new ScenarioGraph("") {
        };

        Account account = new Account();
        member.set(account, "blaat");
        Window.alert("Hello World");

    }

}
