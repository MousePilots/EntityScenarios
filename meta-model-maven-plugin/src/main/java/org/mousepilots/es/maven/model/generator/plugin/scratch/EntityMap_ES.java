package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.MapAttributeES;

/**
 * Models class {@link EntityMap} with persistence type: ENTITY
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(EntityMap.class)
public class EntityMap_ES extends BaseEntity_ES{

     public static volatile MapAttributeES<EntityMap, Phone, Phone> entityEntity;
     public static volatile MapAttributeES<EntityMap, Phone, java.lang.String> entityBasic;
     public static volatile MapAttributeES<EntityMap, Phone, Address> entityEmbeddable;

    private static final org.mousepilots.es.core.model.EntityTypeES<EntityMap> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EntityTypeESImpl<>(
        javax.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE,
        EntityMap.class,
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "EntityMap",
        5,
        EntityMap.class,
        javax.persistence.metamodel.Type.PersistenceType.ENTITY,
        "EntityMap",
        true,
        EntityMap_.class,
        new java.util.HashSet(java.util.Arrays.asList(46, 47, 48)),
        2,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EntityTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     entityEntity = (MapAttributeES<EntityMap, Phone, Phone>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(46);
     entityBasic = (MapAttributeES<EntityMap, Phone, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(47);
     entityEmbeddable = (MapAttributeES<EntityMap, Phone, Address>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(48);
    }
}