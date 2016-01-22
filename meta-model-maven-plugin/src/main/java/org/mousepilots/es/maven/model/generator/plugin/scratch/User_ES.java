package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;

/**
 * Models class {@link User} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(User.class)
public class User_ES extends Person_ES{

     public static final ListAttributeES<User,  Address> addresses;
     public static final ListAttributeES<User,  java.lang.String> emailAddresses;
     public static final CollectionAttributeES<User,  Role> previousRoles;
     public static final CollectionAttributeES<User,  Role> roles;
     public static final SingularAttributeES<User,  java.lang.String> userName;
     public static final SetAttributeES<User,  User> subordinates;
     public static final SingularAttributeES<User,  Account> account;
     public static final SingularAttributeES<User,  WorkEnvironment> workEnvironment;
    static {
     addresses = (ListAttributeES<User, Address>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(21);
     emailAddresses = (ListAttributeES<User, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(22);
     previousRoles = (CollectionAttributeES<User, Role>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(23);
     roles = (CollectionAttributeES<User, Role>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(24);
     userName = (SingularAttributeES<User, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(25);
     subordinates = (SetAttributeES<User, User>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(26);
     account = (SingularAttributeES<User, Account>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(27);
     workEnvironment = (SingularAttributeES<User, WorkEnvironment>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(28);
    }


    private static final org.mousepilots.es.core.model.EntityTypeES<User> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        User.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "User",
        12,
        User.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "User",
        true,
        User_.class,
        new java.util.HashSet(java.util.Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28)),
        8,
        java.util.Arrays.asList(7)
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

}