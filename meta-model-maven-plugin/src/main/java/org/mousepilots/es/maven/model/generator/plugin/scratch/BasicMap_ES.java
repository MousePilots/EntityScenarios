package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.MapAttributeES;

/**
 * Models class {@link BasicMap} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(BasicMap.class)
public class BasicMap_ES extends BaseEntity_ES{

     public static volatile MapAttributeES<BasicMap, java.lang.String, java.lang.String> basicBasic;
     public static volatile MapAttributeES<BasicMap, java.lang.String, Phone> basicEntity;
     public static volatile MapAttributeES<BasicMap, java.lang.String, Address> basicEmbeddable;

    private static final org.mousepilots.es.core.model.EntityTypeES<BasicMap> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        BasicMap.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "BasicMap",
        3,
        BasicMap.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "BasicMap",
        true,
        BasicMap_.class,
        new java.util.HashSet(java.util.Arrays.asList(42, 43, 44)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     basicBasic = (MapAttributeES<BasicMap, java.lang.String, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(42);
     basicEntity = (MapAttributeES<BasicMap, java.lang.String, Phone>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(43);
     basicEmbeddable = (MapAttributeES<BasicMap, java.lang.String, Address>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(44);
    }
}