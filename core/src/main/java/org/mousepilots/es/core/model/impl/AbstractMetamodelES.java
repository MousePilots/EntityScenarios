package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasOrdinal;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.util.CollectionUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 */
public abstract class AbstractMetamodelES implements MetamodelES {

    private static AbstractMetamodelES INSTANCE;

    /**
     * Get an instance of this class.
     *
     * @return An instance of this class.
     */
    public static AbstractMetamodelES getInstance() {
        return INSTANCE;
    }

    private final SortedMap<Integer, TypeES>           ordinalToType = new TreeMap<>();
    private final SortedMap<Integer, AttributeES>      ordinalToAttribute = new TreeMap<>();
    private final SortedMap<Integer, AssociationES>    ordinalToAssociation = new TreeMap<>();
    private final Set<BasicTypeES<?>>                  basicTypes = new TreeSet<>();
    private final Set<EmbeddableTypeES<?>>             embeddableTypes = new TreeSet<>();
    private final Set<ManagedTypeES<?>>                managedTypes = new TreeSet<>();
    private final Set<MappedSuperclassTypeES<?>>       mappedSuperclassTypes = new TreeSet<>();
    private final Set<EntityTypeES<?>>                 entityTypes = new TreeSet<>();
    private final Map<Class, TypeES>                   javaTypeToType = new HashMap<>();

    private static <V extends HasOrdinal,M extends Map<Integer,V>> void putByOrdinal(M map, V value) {
        final V existing = map.put(value.getOrdinal(), value);
        if (existing != null) {
            throw new IllegalStateException("duplicate ordinal " + value.getOrdinal() + " for " + existing + ", " + value);
        }
    }

    /**
     * Set an instance of this class.
     *
     * @param instance The instance of this class to set.
     * @throws IllegalStateException If an instance was already created before.
     */
    protected void setInstance(AbstractMetamodelES instance) throws IllegalStateException {
        if (INSTANCE != null) {
            throw new IllegalStateException("instance allready set");
        } else {
            INSTANCE = instance;
        }
    }

    /**
     * Get a specific type by its ordinal.
     *
     * @param ordinal The ordinal of the type to get.
     * @return The {@link TypeES} instance for the specified {@code ordinal}, or {@code null} if no type with the specified {@code ordinal} exists.
     */
    public TypeES getType(int ordinal) {
        return ordinalToType.get(ordinal);
    }


    private final TypeVisitor<Void, Void> typeRegistrar = new TypeVisitor<Void, Void>() {

        private void performCommonRegistration(TypeES t) {
            putByOrdinal(ordinalToType, t);
            javaTypeToType.put(t.getJavaType(), t);
        }

        private <T extends ManagedTypeES> void performManagedTypeRegistration(Collection<T> collection, T t) {
            performCommonRegistration(t);
            final Class proxyJavaType = t.getProxyJavaType();
            if(proxyJavaType!=null){
                javaTypeToType.put(proxyJavaType, t);
            }
            CollectionUtils.ensureAdded(collection, t);
            CollectionUtils.ensureAdded(managedTypes, t);
        }

        @Override
        public Void visit(BasicTypeES t, Void arg) {
            performCommonRegistration(t);
            CollectionUtils.ensureAdded(basicTypes, t);
            return null;
        }

        @Override
        public Void visit(EmbeddableTypeES t, Void arg) {
            performManagedTypeRegistration(AbstractMetamodelES.this.embeddableTypes, t);
            return null;
        }

        @Override
        public Void visit(MappedSuperclassTypeES t, Void arg) {
            performManagedTypeRegistration(AbstractMetamodelES.this.mappedSuperclassTypes, t);
            return null;
        }

        @Override
        public Void visit(EntityTypeES t, Void arg) {
            performManagedTypeRegistration(AbstractMetamodelES.this.entityTypes, t);
            return null;
        }
    };

    protected AbstractMetamodelES(Collection<TypeESImpl<?>> types) {
         //register types
        for (TypeESImpl t : types) {
            t.accept(typeRegistrar, null);
        }
        
        //register declared attributes and associations
        for(ManagedTypeES t : managedTypes){
             //register declared attributes only
             final Set<AttributeES> declaredAttributes = t.getDeclaredAttributes();
             for(AttributeES a : declaredAttributes){
                  putByOrdinal(ordinalToAttribute, a);
                  
                  for(AssociationTypeES associationType : AssociationTypeES.values()){
                       final AssociationES association = a.getAssociation(associationType);
                       if(association!=null){
                            putByOrdinal(ordinalToAssociation, association);
                       }
                  }
             }
        }
        setInstance(this);
        for(int round=0; round<2; round++){
            for (TypeESImpl t : types) {
                t.init(round);
            }
        }
    }

    @Override
    public <X> EntityTypeES<X> entity(Class<X> cls) {
        for (EntityType entityType : entityTypes) {
            if (entityType.getJavaType() == cls) {
                return (EntityTypeES<X>) entityType;
            }
        }
        return null;
    }
    
    @Override
    public <X> TypeES<X> type(Class<X> cls) {
        return javaTypeToType.get(cls);
    }
    

    @Override
    public <X> ManagedTypeES<X> managedType(Class<X> cls) {
        return (ManagedTypeES<X>) javaTypeToType.get(cls);
    }

    @Override
    public <X, M extends ManagedTypeES<X>> M managedType(Class<X> javaType, Class<M> managedTypeClass) {
        return (M) managedType(javaType);
    }

    @Override
    public <X> EmbeddableTypeES<X> embeddable(Class<X> cls) {
        for (EmbeddableTypeES embeddableType : embeddableTypes) {
            if (embeddableType.getJavaType() == cls) {
                return embeddableType;
            }
        }
        return null;
    }

    @Override
    public Set<ManagedType<?>> getManagedTypes() {
        return Collections.unmodifiableSet(managedTypes);
    }

    @Override
    public Set<EntityType<?>> getEntities() {
        return Collections.unmodifiableSet(entityTypes);
    }

    @Override
    public Set<EmbeddableType<?>> getEmbeddables() {
        return (Set) Collections.unmodifiableSet(embeddableTypes);
    }

    /**
     * Get a specific attribute by its ordinal.
     *
     * @param ordinal The ordinal of the attribute to get.
     * @return The {@link AttributeES} instance for the specified {@code ordinal}, or {@code null} if no attribute with the specified {@code ordinal} exists.
     */
    public AttributeES getAttribute(int ordinal) {
        return ordinalToAttribute.get(ordinal);
    }
    
    public AssociationES getAssociation(int ordinal){
         return ordinalToAssociation.get(ordinal);
    }
}
