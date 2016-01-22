package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link Phone} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Phone.class)
public class Phone_ES extends BaseEntity_ES{

     public static volatile SingularAttributeES<Phone,  User> owner;
     public static volatile SingularAttributeES<Phone,  java.lang.String> phoneNumber;

    private static final org.mousepilots.es.core.model.EntityTypeES<Phone> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        Phone.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "Phone",
        10,
        Phone.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "Phone",
        true,
        Phone_.class,
        new java.util.HashSet(java.util.Arrays.asList(59, 60)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     owner = (SingularAttributeES<Phone, User>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(59);
     phoneNumber = (SingularAttributeES<Phone, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(60);
    }
}