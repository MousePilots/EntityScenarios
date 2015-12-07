package org.mousepilots.es.server;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.mousepilots.es.change.Change;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableVersionedChange;
import org.mousepilots.es.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.change.exception.IllegalChangeException;
import org.mousepilots.es.change.exception.Reason;
import org.mousepilots.es.change.impl.Create;
import org.mousepilots.es.change.impl.Delete;
import org.mousepilots.es.change.impl.EmbeddableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableSingularBasicAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToEmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableSingularBasicAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToEmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.MetaModelES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.util.Maps;

/**
 * @author Roy Cleven
 *
 * Only executes the changes, doesn't validate them and doesn't care if it's
 * bidirectional.
 *
 */
public class ServerChangeVisitor implements ChangeVisitor {

    private final EntityManager entityManager;

    private final MetaModelES metamodel;

    private final Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToGeneratedId = new HashMap<>();

    public ServerChangeVisitor(EntityManager entityManager, MetaModelES metamodel) {
        this.entityManager = entityManager;
        this.metamodel = metamodel;
    }

    private void assertEqualVersion(AbstractIdentifiableVersionedChange change, Object instance) throws IllegalChangeException {
        final TypeES type = change.getType();
        if (type instanceof IdentifiableTypeES) {
            final IdentifiableTypeES identifiableType = (IdentifiableTypeES) type;
            final SingularAttributeES versionAttribute = identifiableType.getVersion(null);
            if (versionAttribute != null) {
                final Serializable version = change.getVersion();
                final Object storedVersion = versionAttribute.getJavaMember().get(instance);
                if (!Objects.equals(version, storedVersion)) {
                    throw new IllegalChangeException(change, Reason.VERSION_MISMATCH);
                }
            }
        }
    }

    private Object getInstance(Change change, Object id) throws IllegalChangeException {
        final Class javaType = change.getType().getJavaType();
        return getInstance(javaType, id, change);
    }

    private Object getInstance(final Class javaType, Object id, Change change) throws IllegalChangeException {
        final Object instance = entityManager.find(javaType, id);
        if (instance == null) {
            throw new IllegalChangeException(change, Reason.ENTITY_NOT_FOUND);
        }
        return instance;
    }

    private Object getInstance(Change change, IdentifiableTypeES targetType, Object id) throws IllegalChangeException {
        return getInstance(targetType.getJavaType(), id, change);
    }

    private Serializable getPersistentId(IdentifiableTypeES type, Serializable id) {
        final Serializable mappedId = Maps.get(typeToClientIdToGeneratedId, type, id);
        return mappedId == null ? id : mappedId;
    }

    private Object getIdentifiableSource(AbstractIdentifiableVersionedChange update) throws IllegalChangeException {
        Serializable id = getPersistentId((IdentifiableTypeES) update.getType(), update);
        Object instance = getInstance(update, id);
        assertEqualVersion(update, instance);
        return instance;
    }

    private Object getIdentifiable(Change update, IdentifiableTypeES identifiableType, Serializable id) throws IllegalChangeException {
        Serializable pid = getPersistentId(identifiableType, id);
        Object instance = getInstance(update, pid);
        return instance;
    }

    private Object getEmbeddable(AbstractNonIdentifiableUpdate update) throws IllegalChangeException {
        //TODO, fix when container isn't embeddable.
        final Object source = getInstance(update.getContainer().getClass(), update.getContainerId(), update);
        final Object embeddable = update.getContainerAttribute().getJavaMember().get(source);
        return embeddable;
    }

    private void updateList(final MemberES listMember, Object instance, Change change, List removals, List additions) throws IllegalChangeException {
        final Collection list = listMember.get(instance);
        for (Object removal : removals) {
            if (!list.remove(removal)) {
                throw new IllegalChangeException(change, Reason.NO_SUCH_VALUE);
            }
        }
        for (Object addition : additions) {
            if (!list.add(addition)) {
                throw new IllegalChangeException(change, Reason.DUPLICATE_VALUE);
            }
        }
        listMember.set(instance, list);
    }

    private void updateListEntity(final MemberES listMember, Object instance, Change change, List<Serializable> removals, List<Serializable> additions, IdentifiableTypeES targetEntity) throws IllegalChangeException {
        final Collection list = listMember.get(instance);
        for (Serializable removal : removals) {
            final Object target = getInstance(change, targetEntity, removal);
            if (!list.remove(target)) {
                throw new IllegalChangeException(change, Reason.NO_SUCH_VALUE);
            }
        }
        for (Serializable addition : additions) {
            final Object target = getInstance(change, targetEntity, addition);
            if (!list.add(target)) {
                throw new IllegalChangeException(change, Reason.DUPLICATE_VALUE);
            }
        }
        listMember.set(instance, list);
    }

    @Override
    public void visit(Create create) throws IllegalChangeException {
        final IdentifiableTypeES type = (IdentifiableTypeES) create.getType();
        final SingularAttributeES idAttribute = type.getId(null);
        final Object instance = type.createInstance();
        final MemberES idMember = idAttribute.getJavaMember();

        if (idAttribute.isGenerated()) {
            entityManager.persist(instance);
            final Map<Serializable, Serializable> clientIdToGeneratedId
                    = Maps.getOrCreate(typeToClientIdToGeneratedId, type, HashMap::new);
            clientIdToGeneratedId.put(create.getId(), idMember.get(instance));
        } else {
            try {
                idMember.set(instance, create.getId());
                entityManager.persist(instance);
            } catch (EntityExistsException e) {
                throw new IllegalChangeException(create, Reason.ENTITY_EXISTS);
            }
        }
    }

    @Override
    public void visit(Delete delete) throws IllegalChangeException {
        Object instance = getInstance(delete, delete.getId());
        assertEqualVersion(delete, instance);
        entityManager.remove(instance);
    }

    @Override
    public void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update) {
        final Object instance = getIdentifiableSource(update);
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateList(listMember, instance, update, update.getAdditions(), update.getRemovals());
    }

    @Override
    public void visit(IdentifiableSingularBasicAttributeUpdate update) {
        Object instance = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(instance, update.getNewValue());
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update) throws IllegalChangeException {
        Object embeddable = getEmbeddable(update);
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateList(listMember, embeddable, update, update.getAdditions(), update.getRemovals());
    }

    @Override
    public void visit(IdentifiableToIdentifiableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getAttribute().getJavaType());
        final Object target = getInstance(update, targetEntity, update.getNewValue());
        update.getAttribute().getJavaMember().set(source, target);
    }

    @Override
    public void visit(IdentifiableToEmbeddableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(source, update.getNewValue());
    }

    @Override
    public void visit(EmbeddableSingularBasicAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        update.getUpdatedAttribute().getJavaMember().set(embeddable, update.getNewValue());
    }

    @Override
    public void visit(IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        final Object instance = getIdentifiableSource(update);
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateList(listMember, instance, update, update.getAdditions(), update.getRemovals());
    }

    @Override
    public void visit(IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getAttribute().getJavaType());
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateListEntity(listMember, source, update, update.getAdditions(), update.getRemovals(), targetEntity);
    }

    @Override
    public void visit(EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateList(listMember, embeddable, update, update.getAdditions(), update.getRemovals());
    }

    @Override
    public void visit(EmbeddableToEmbeddableSingularAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        update.getUpdatedAttribute().getJavaMember().set(embeddable, update.getNewValue());
    }

    @Override
    public void visit(EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getUpdatedAttribute().getJavaType());
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateListEntity(listMember, embeddable, update, update.getAdditions(), update.getRemovals(), targetEntity);
    }

    @Override
    public void visit(EmbeddableToIdentifiableSingularAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getUpdatedAttribute().getJavaType());
        final Object target = getInstance(update, targetEntity, update.getNewValue());
        update.getUpdatedAttribute().getJavaMember().set(embeddable, target);
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getElementType().getJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey())) {
                final Object value = getInstance(update, targetEntity, removal.getValue());
                if (!map.remove(removal.getKey(), value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object value = getInstance(update, targetEntity, addition.getValue());
            if (map.putIfAbsent(addition.getKey(), value) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MemberES mapMember = update.getAttribute().getJavaMember();
        final Map map = mapMember.get(source);
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey())) {
                if (!map.remove(removal.getKey(), removal.getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            if (map.putIfAbsent(addition.getKey(), addition.getValue()) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(IdentifiableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES valueEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getElementType().getJavaType());
        final IdentifiableTypeES keyEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getKeyJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            final Object key = getInstance(update, keyEntity, removal.getKey());
            if (map.containsKey(key)) {
                final Object value = getInstance(update, valueEntity, removal.getValue());
                if (!map.remove(key, value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object key = getInstance(update, keyEntity, addition.getKey());
            final Object value = getInstance(update, valueEntity, addition.getValue());
            if (map.putIfAbsent(key, value) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(IdentifiableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES keyEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getKeyJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            final Object key = getInstance(update, keyEntity, removal.getKey());
            if (map.containsKey(key)) {
                if (!map.remove(key, removal.getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object key = getInstance(update, keyEntity, addition.getKey());
            if (map.putIfAbsent(key, addition.getValue()) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(EmbeddableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES valueEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getElementType().getJavaType());
        final IdentifiableTypeES keyEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getKeyJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            final Object key = getInstance(update, keyEntity, removal.getKey());
            if (map.containsKey(key)) {
                final Object value = getInstance(update, valueEntity, removal.getValue());
                if (!map.remove(key, value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object key = getInstance(update, keyEntity, addition.getKey());
            final Object value = getInstance(update, valueEntity, addition.getValue());
            if (map.putIfAbsent(key, value) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES keyEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getKeyJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            final Object key = getInstance(update, keyEntity, removal.getKey());
            if (map.containsKey(key)) {
                if (!map.remove(key, removal.getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object key = getInstance(update, keyEntity, addition.getKey());
            if (map.putIfAbsent(key, addition.getValue()) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(mapAttribute.getElementType().getJavaType());
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey())) {
                final Object value = getInstance(update, targetEntity, removal.getValue());
                if (!map.remove(removal.getKey(), value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            final Object value = getInstance(update, targetEntity, addition.getValue());
            if (map.putIfAbsent(addition.getKey(), value) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MemberES mapMember = update.getUpdatedAttribute().getJavaMember();
        final Map map = mapMember.get(source);
        for (SimpleEntry removal : (List<SimpleEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey())) {
                if (!map.remove(removal.getKey(), removal.getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (SimpleEntry addition : (List<SimpleEntry>) update.getAdditions()) {
            if (map.putIfAbsent(addition.getKey(), addition.getValue()) != null) {
                throw new IllegalChangeException(update, Reason.KEY_ALREADY_PRESENT);
            }
        }
        mapMember.set(source, map);
    }
}
