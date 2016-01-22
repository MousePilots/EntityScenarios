package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link WorkEnvironment} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(WorkEnvironment.class)
public class WorkEnvironment_ES extends BaseEntity_ES{

     public static volatile SingularAttributeES<WorkEnvironment,  java.lang.String> officeLocation;
     public static volatile SingularAttributeES<WorkEnvironment,  java.lang.String> officeNumber;

    private static final org.mousepilots.es.core.model.EntityTypeES<WorkEnvironment> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        WorkEnvironment.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "WorkEnvironment",
        13,
        WorkEnvironment.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "WorkEnvironment",
        true,
        WorkEnvironment_.class,
        new java.util.HashSet(java.util.Arrays.asList(37, 38)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     officeLocation = (SingularAttributeES<WorkEnvironment, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(37);
     officeNumber = (SingularAttributeES<WorkEnvironment, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(38);
    }
}