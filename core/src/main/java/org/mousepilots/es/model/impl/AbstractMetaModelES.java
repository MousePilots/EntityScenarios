package org.mousepilots.es.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.model.EmbeddableTypeES;
import org.mousepilots.es.model.EntityTypeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MetaModelES;

/**
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public abstract class AbstractMetaModelES implements MetaModelES {

    private static MetaModelES INSTANCE;

    /**
     * Get an instance of this class.
     * @return An instance of this class.
     */
    public static MetaModelES getInstance() {
        return INSTANCE;
    }

    /**
     * Set an instance of this class.
     * @param instance The instance of this class to set.
     * @throws IllegalStateException If in instance was already created before.
     */
    protected void setInstance(MetaModelES instance){
        if (INSTANCE != null) {
            throw new IllegalStateException("Cannot set an instance because its already created.");
        }
        INSTANCE = instance;
    }

    private final Set<ManagedType<?>> managedTypes;
    private final Set<EntityType<?>> entities;
    private final Set<EmbeddableType<?>> embeddables;
    private final Map<ManagedTypeES, SortedSet<ManagedTypeES>> managedTypeToSuperManagedTypes;
    private final Map<ManagedTypeES, SortedSet<ManagedTypeES>> managedTypeToSubManagedTypes;

    /**
     * Create a new instance of this class.
     * @param managedTypes A set of all managed types.
     * @param entities A set of all entities.
     * @param embeddables A set of all embeddables.
     */
    protected AbstractMetaModelES(Set<ManagedType<?>> managedTypes,
            Set<EntityType<?>> entities, Set<EmbeddableType<?>> embeddables) {
        this.managedTypes = managedTypes;
        //TODO opslitsen van managed types tot entiteiten en embeddables.
        //TODO opsplitsen van managed types tot attributen.
        //Super types en sub types, Map van managed type naar Set.
        //1 Javatype naar managedtype
        //2
        this.entities = entities;
        this.embeddables = embeddables;
        this.managedTypeToSuperManagedTypes = new HashMap<>();
        this.managedTypeToSubManagedTypes = new HashMap<>();
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
    public <X, M extends ManagedTypeES<X>> M managedType(Class<X> javaType, Class<M> managedTypeClass){
        return (M) managedType(javaType);
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