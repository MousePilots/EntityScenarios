package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.CollectionAttributeES;

/**
 * Models class {@link Role} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Role.class)
public class Role_ES extends BaseEntity_ES{

     public static volatile SingularAttributeES<Role,  java.lang.String> name;
     public static volatile CollectionAttributeES<Role,  User> users;

    private static final org.mousepilots.es.core.model.EntityTypeES<Role> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        Role.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "Role",
        11,
        Role.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "Role",
        true,
        Role_.class,
        new java.util.HashSet(java.util.Arrays.asList(56, 57)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     name = (SingularAttributeES<Role, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(56);
     users = (CollectionAttributeES<Role, User>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(57);
    }
}