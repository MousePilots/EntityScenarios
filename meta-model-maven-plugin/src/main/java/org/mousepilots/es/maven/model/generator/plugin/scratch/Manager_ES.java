package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link Manager} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Manager.class)
public class Manager_ES extends User_ES{

     public static volatile SingularAttributeES<Manager,  Address> managerAddress;
     public static volatile SingularAttributeES<Manager,  ManagerAccount> account;

    private static final org.mousepilots.es.core.model.EntityTypeES<Manager> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        Manager.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "Manager",
        7,
        Manager.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "Manager",
        true,
        Manager_.class,
        new java.util.HashSet(java.util.Arrays.asList(49, 50)),
        12,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     managerAddress = (SingularAttributeES<Manager, Address>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(49);
     account = (SingularAttributeES<Manager, ManagerAccount>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(50);
    }
}