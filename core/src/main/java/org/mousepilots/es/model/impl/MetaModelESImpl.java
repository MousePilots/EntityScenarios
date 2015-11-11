package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.EntityTypeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MetaModelES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 */
public class MetaModelESImpl implements MetaModelES {

    private final Set<ManagedType<?>> managedTypes;
    private final Set<EntityType<?>> entities;
    private final Set<EmbeddableType<?>> embeddables;

    public MetaModelESImpl(Set<ManagedType<?>> managedTypes,
            Set<EntityType<?>> entities, Set<EmbeddableType<?>> embeddables) {
        this.managedTypes = managedTypes;
        this.entities = entities;
        this.embeddables = embeddables;
    }

    @Override
    public <X> EntityTypeES<X> entity(Class<X> cls) {
        for (EntityType entityType : entities){
            if (entityType.getJavaType() == cls) {
                return (EntityTypeES<X>)entityType;
            }
        }
        return null;
    }

    @Override
    public <X> ManagedTypeES<X> managedType(Class<X> cls) {
        for (ManagedType managedType : managedTypes){
            if (managedType.getJavaType() == cls) {
                return (ManagedTypeES<X>)managedType;
            }
        }
        return null;
    }

    @Override
    public <X> EmbeddableTypeES<X> embeddable(Class<X> cls) {
       for (EmbeddableType embeddableType : embeddables){
            if (embeddableType.getJavaType() == cls) {
                return (EmbeddableTypeES<X>)embeddableType;
            }
        }
        return null;
    }

    @Override
    public Set<ManagedType<?>> getManagedTypes() {
        return managedTypes;
    }

    @Override
    public Set<EntityType<?>> getEntities() {
        return entities;
    }

    @Override
    public Set<EmbeddableType<?>> getEmbeddables() {
        return embeddables;
    }
}