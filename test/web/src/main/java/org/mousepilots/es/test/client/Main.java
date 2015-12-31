package org.mousepilots.es.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.impl.Getter;
import org.mousepilots.es.core.model.impl.PropertyMember;
import org.mousepilots.es.core.model.impl.Setter;
import org.mousepilots.es.test.domain.entities.Account;
import org.mousepilots.es.test.domain.entities.Account_;

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
