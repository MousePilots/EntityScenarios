package org.mousepilots.es.core.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MetaModelES;
import org.mousepilots.es.core.model.TypeES;

/**
 * This class is an abstract implementation of the MetaModelES interface.
 * This class will hold of the instances of the meta model classes.
 * And it will be used to interact with the extended meta model programmatically.
 * An different version will be generated that implements the abstract methods from this class.
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 */
public abstract class AbstractMetaModelES implements MetaModelES {

    private static AbstractMetaModelES INSTANCE;

    /**
     * Get an instance of this class.
     * @return An instance of this class.
     */
    public static AbstractMetaModelES getInstance() {
        return INSTANCE;
    }

    /**
     * Set an instance of this class.
     * @param instance The instance of this class to set.
     * @throws IllegalStateException If an instance was already created before.
     */
    protected void setInstance(AbstractMetaModelES instance){
        if (INSTANCE != null) {
            throw new IllegalStateException("Cannot set an instance because its already created.");
        }
        INSTANCE = instance;
    }

    private final Set<ManagedType<?>> managedTypes = new HashSet<>();
    private final Set<EntityType<?>> entities = new HashSet<>();
    private final Set<EmbeddableType<?>> embeddables = new HashSet<>();
    private final Map<ManagedTypeES, SortedSet<ManagedTypeES>> managedTypeToSuperManagedTypes = new HashMap<>();
    private final Map<ManagedTypeES, SortedSet<ManagedTypeES>> managedTypeToSubManagedTypes = new HashMap<>();
    private final SortedMap<Integer, TypeES> ordinalToType = new TreeMap<>();
    private final SortedMap<Integer, AttributeES> ordinalToAttribute = new TreeMap<>();

    /**
     * Register a specific {@link TypeES} to the meta model.
     * @param type The type to register.
     */
    protected final void register(TypeES type){
        ordinalToType.put(type.getOrdinal(), type);
    }

    /**
     * Register a specific {@link AttributeES} to the meta model.
     * @param attribute The attribute to register.
     */
    protected final void register(AttributeES attribute){
        ordinalToAttribute.put(attribute.getOrdinal(), attribute);
    }

    /**
     * Get a specific type by its ordinal.
     * @param ordinal The ordinal of the type to get.
     * @return The {@link TypeES} instance for the specified {@code ordinal}, or {@code null} if no type with the specified {@code ordinal} exists.
     */
    public TypeES getType(int ordinal){
        return ordinalToType.get(ordinal);
    }

    protected AbstractMetaModelES(){}

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

    /**
     * Get a specific attribute by its ordinal.
     * @param ordinal The ordinal of the attribute to get.
     * @return The {@link AttributeES} instance for the specified {@code ordinal}, or {@code null} if no attribute with the specified {@code ordinal} exists.
     */
    public AttributeES getAttribute(int ordinal) {
        return ordinalToAttribute.get(ordinal);
    }
}