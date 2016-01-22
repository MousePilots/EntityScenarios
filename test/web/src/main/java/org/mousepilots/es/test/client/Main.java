package org.mousepilots.es.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.impl.Getter;
import org.mousepilots.es.core.model.impl.PropertyMember;
import org.mousepilots.es.core.model.impl.Setter;
import org.mousepilots.es.test.domain.Account;
import org.mousepilots.es.test.domain.Account_;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author geenenju
 */
public class Main implements EntryPoint{

     @Override
     public void onModuleLoad() {

          final SingularAttribute<Account, String> description = Account_.description;

          MemberES member = new PropertyMember(
               Account.class, "description",
               (Getter<Account,String>) Account::getDescription,
               (Setter<Account,String>) Account::setDescription,
               0x00000001
          );

          Account account = new Account();
          member.set(account, "blaat");
          Window.alert("Hello World");


     }

}
