package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.MapAttributeES;

/**
 * Models class {@link EmbeddableMap} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(EmbeddableMap.class)
public class EmbeddableMap_ES extends BaseEntity_ES{

     public static volatile MapAttributeES<EmbeddableMap, PhoneType, Address> embeddableEmbeddable;
     public static volatile MapAttributeES<EmbeddableMap, PhoneType, Phone> embeddableEntity;
     public static volatile MapAttributeES<EmbeddableMap, PhoneType, java.lang.String> embeddableBasic;

    private static final org.mousepilots.es.core.model.EntityTypeES<EmbeddableMap> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        EmbeddableMap.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "EmbeddableMap",
        4,
        EmbeddableMap.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "EmbeddableMap",
        true,
        EmbeddableMap_.class,
        new java.util.HashSet(java.util.Arrays.asList(39, 40, 41)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     embeddableEmbeddable = (MapAttributeES<EmbeddableMap, PhoneType, Address>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(39);
     embeddableEntity = (MapAttributeES<EmbeddableMap, PhoneType, Phone>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(40);
     embeddableBasic = (MapAttributeES<EmbeddableMap, PhoneType, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(41);
    }
}