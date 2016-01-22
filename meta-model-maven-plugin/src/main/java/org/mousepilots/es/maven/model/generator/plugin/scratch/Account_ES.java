package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link Account} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Account.class)
public class Account_ES extends BaseEntity_ES{

     public static volatile SingularAttributeES<Account,  java.lang.String> description;

    private static final org.mousepilots.es.core.model.EntityTypeES<Account> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        Account.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "Account",
        0,
        Account.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "Account",
        true,
        Account_.class,
        new java.util.HashSet(java.util.Arrays.asList(58)),
        2,
        java.util.Arrays.asList(6)
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     description = (SingularAttributeES<Account, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(58);
    }
}