package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link BaseEntity} with persistence type: MAPPED_SUPERCLASS
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(BaseEntity.class)
public class BaseEntity_ES {

     public static volatile SingularAttributeES<BaseEntity,  java.lang.Long> id;
     public static volatile SingularAttributeES<BaseEntity,  java.lang.Long> version;

    private static final org.mousepilots.es.core.model.MappedSuperclassTypeES<BaseEntity> INSTANCE_ES = new org.mousepilots.es.core.model.impl.MappedSuperclassTypeESImpl<>(
        35,
        35,
        36,
        36,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "BaseEntity",
        2,
        BaseEntity.class,
        javax.persistence.metamodel.Type.PersistenceType.MAPPED_SUPERCLASS,
        "BaseEntity",
        true,
        BaseEntity_.class,
        new java.util.HashSet(java.util.Arrays.asList(35, 36)),
        -1,
        java.util.Arrays.asList(0, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13)
        );

    public static org.mousepilots.es.core.model.TypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     id = (SingularAttributeES<BaseEntity, java.lang.Long>) INSTANCE_ES.getId(java.lang.Long.class);
     version = (SingularAttributeES<BaseEntity, java.lang.Long>) INSTANCE_ES.getVersion(java.lang.Long.class);
    }
}