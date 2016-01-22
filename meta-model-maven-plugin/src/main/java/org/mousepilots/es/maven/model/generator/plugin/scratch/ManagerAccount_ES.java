package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.ListAttributeES;

/**
 * Models class {@link ManagerAccount} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(ManagerAccount.class)
public class ManagerAccount_ES extends Account_ES{

     public static volatile ListAttributeES<ManagerAccount,  User> subordinates;

    private static final org.mousepilots.es.core.model.EntityTypeES<ManagerAccount> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        ManagerAccount.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "ManagerAccount",
        6,
        ManagerAccount.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "ManagerAccount",
        true,
        ManagerAccount_.class,
        new java.util.HashSet(java.util.Arrays.asList(45)),
        0,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     subordinates = (ListAttributeES<ManagerAccount, User>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(45);
    }
}