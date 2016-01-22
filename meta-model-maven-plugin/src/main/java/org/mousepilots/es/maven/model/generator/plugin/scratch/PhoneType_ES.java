package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link PhoneType} with persistence type: EMBEDDABLE
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(PhoneType.class)
public class PhoneType_ES {

     public static volatile SingularAttributeES<PhoneType,  java.lang.String> type;

    private static final org.mousepilots.es.core.model.EmbeddableTypeES<PhoneType> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl<>(
        "PhoneType",
        9,
        PhoneType.class,
        javax.persistence.metamodel.Type.PersistenceType.EMBEDDABLE,
        "PhoneType",
        true,
        PhoneType_.class,
        new java.util.HashSet(java.util.Arrays.asList(29)),
        -1,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EmbeddableTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     type = (SingularAttributeES<PhoneType, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(29);
    }
}