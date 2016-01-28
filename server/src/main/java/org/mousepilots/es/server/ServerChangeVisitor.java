package org.mousepilots.es.server;

import org.mousepilots.es.core.change.ExecutionSummary;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableVersionedChange;
import org.mousepilots.es.core.change.abst.AbstractNonIdentifiableUpdate;
import org.mousepilots.es.core.change.exception.IllegalChangeException;
import org.mousepilots.es.core.change.exception.Reason;
import org.mousepilots.es.core.change.impl.Create;
import org.mousepilots.es.core.change.impl.Delete;
import org.mousepilots.es.core.change.impl.EmbeddableAndAttribute;
import org.mousepilots.es.core.change.impl.EmbeddableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableSingularBasicAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToEmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableSingularBasicAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToEmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.MetaModelES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasValueEntry;
import org.mousepilots.es.core.util.Maps;


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
    
    private ExecutionSummaryImpl executionSummary;

    public ServerChangeVisitor(EntityManager entityManager, MetaModelES metamodel) {
        this.entityManager = entityManager;
        this.metamodel = metamodel;
        this.executionSummary = new ExecutionSummaryImpl();
    }

    /**
     * checks if the version of the change and if the version of the object
     * are the same. This will throw an {@link IllegalChangeException} when
     * the versions are not the same.
     * @param change the change which contains the version
     * @param instance the object which has the second version
     * @throws IllegalChangeException If the versions are different
     */
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

    /**
     * Gets the instance based on the type and the id provided.
     * @param change change containing the type of instance which is requested
     * @param id the id of the instance you want
     * @return an object which is an instance based on the id and javatype
     * @throws IllegalChangeException 
     */
    private Object getInstance(Change change, Object id) throws IllegalChangeException {
        final Class javaType = change.getType().getJavaType();
        return getInstance(javaType, id, change);
    }

    /**
     * Gets the instance based on the type and the id provided.
     * @param javaType the type of the instance
     * @param id the id of the requested instance
     * @param change the change in where the request is needed
     * @return an object which is an instance based on the id and javatype
     * @throws IllegalChangeException 
     */
    private Object getInstance(final Class javaType, Object id, Change change) throws IllegalChangeException {
        final Object instance = entityManager.find(javaType, id);
        if (instance == null) {
            throw new IllegalChangeException(change, Reason.ENTITY_NOT_FOUND);
        }
        return instance;
    }

    /**
     * Gets the instance based on the type and the id provided
     * @param change the change in where the request is needed
     * @param targetType IdentifiableTypeES containing the type for the instance
     * @param id the id of the requested instance
     * @return an object which is an instance based on the id and javatype
     * @throws IllegalChangeException 
     */
    private Object getInstance(Change change, IdentifiableTypeES targetType, Object id) throws IllegalChangeException {
        return getInstance(targetType.getJavaType(), id, change);
    }

    /**
     * Gets the id for an instance which is saved based on the id.
     * @param type {@link IdentifiableTypeES} containing the type for the instance which has the id
     * @param id current known id for the requested instance
     * @return id for the saved version of the instance
     */
    private Serializable getPersistentId(IdentifiableTypeES type, Serializable id) {
        final Serializable mappedId = Maps.get(typeToClientIdToGeneratedId, type, id);
        return mappedId == null ? id : mappedId;
    }

    /**
     * Gets the instance based on the type and id provided from the change and
     * and checks if the retrieved instance matches the version of the change.
     * @param update the change containing the needed information
     * (id of the instance, type of the instance, version of the instance)
     * @return an instance of the javatype based on the id.
     * @throws IllegalChangeException If the versions don't match or if the instance doesn't exists.
     */
    private Object getIdentifiableSource(AbstractIdentifiableVersionedChange update) throws IllegalChangeException {
        Serializable id = getPersistentId((IdentifiableTypeES) update.getType(), update);
        Object instance = getInstance(update, id);
        assertEqualVersion(update, instance);
        return instance;
    }

    /**
     * 
     * @param update
     * @param identifiableType
     * @param id
     * @return
     * @throws IllegalChangeException 
     */
    private Object getIdentifiable(Change update, IdentifiableTypeES identifiableType, Serializable id) throws IllegalChangeException {
        Serializable pid = getPersistentId(identifiableType, id);
        Object instance = getInstance(update, pid);
        return instance;
    }

    /**
     * 
     * @param update the change which contains the embeddable which is needed.
     * @return embeddable which is getting changed
     * @throws IllegalChangeException 
     */
    private Object getEmbeddable(AbstractNonIdentifiableUpdate update) throws IllegalChangeException {
        AttributeES containingAttribute = update.getContainerAttribute();
        Object container = getInstance(containingAttribute.getClass(), update.getContainerId(), update);
        for(EmbeddableAndAttribute eaa : (List<EmbeddableAndAttribute>) update.getContainerEmbeddables()){
            container = containingAttribute.getJavaMember().get(container);
            if(container instanceof Collection){
                Collection collection = (Collection) container;
                for (Object embeddable : collection) {
                    if(Objects.equals(eaa.getEmbeddable(), embeddable)){
                        container = embeddable;
                    }
                }
            }
            containingAttribute = eaa.getAttribute();
        }
        final Object embeddable = containingAttribute.getJavaMember().get(container);
        // possible check if the attribute is an list
        return embeddable;
    }

    /**
     * Updates the list of the given instance. This method is ment for updating a list which contains non-identifiables (Embeddables/Basics)
     * @param listMember member containing the get/set for the list
     * @param instance instance where the update has to happen on
     * @param change the change which contains the list changes.
     * @param removals all the instances which need to be removed from the list
     * @param additions all the instances which need to be added to the list
     * @throws IllegalChangeException If an instance is unable to be removed. If an instance is unable to be added.
     */
    private static void updateListOfNonIdentifiables(final MemberES listMember, Object instance, Change change, List removals, List additions) throws IllegalChangeException {
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

    /**
     * Updates the list of the given instance. This method is ment for updating a list which contains non-identifiables (Embeddables/Basics)
     * @param listMember member containing the get/set for the list
     * @param instance instance where the update has to happen on
     * @param change the change which contains the list changes.
     * @param removals all the instances which need to be removed from the list
     * @param additions all the instances which need to be added to the list
     * @throws IllegalChangeException If an instance is unable to be removed. If an instance is unable to be added.
     */
    private void updateListOfIdentifiables(final MemberES listMember, Object instance, Change change, List<Serializable> removals, List<Serializable> additions, IdentifiableTypeES targetEntity) throws IllegalChangeException {
        final Collection list = listMember.get(instance);
        for (Serializable id : removals) {
            final Object target = getIdentifiable(change, targetEntity, id);
            if (!list.remove(target)) {
                throw new IllegalChangeException(change, Reason.NO_SUCH_VALUE);
            }
        }
        for (Serializable id : additions) {
            final Object target = getIdentifiable(change, targetEntity, id);
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
        executionSummary.addClientIdToPersistetId(type, create.getId(), idMember.get(instance));
        executionSummary.addCrudToChangeItem(CRUD.DELETE, (Serializable)instance);
    }

    @Override
    public void visit(Delete delete) throws IllegalChangeException {
        Object instance = getInstance(delete, delete.getId());
        assertEqualVersion(delete, instance);
        entityManager.remove(instance);
        executionSummary.addCrudToChangeItem(CRUD.DELETE, (Serializable)instance);
    }

    @Override
    public void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update) {
        final Object instance = getIdentifiableSource(update);
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateListOfNonIdentifiables(listMember, instance, update, update.getAdditions(), update.getRemovals());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)instance);
    }

    @Override
    public void visit(IdentifiableSingularBasicAttributeUpdate update) {
        Object instance = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(instance, update.getNewValue());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)instance);
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update) throws IllegalChangeException {
        Object embeddable = getEmbeddable(update);
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateListOfNonIdentifiables(listMember, embeddable, update, update.getAdditions(), update.getRemovals());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(IdentifiableToIdentifiableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = metamodel.managedType(update.getAttribute().getJavaType(),IdentifiableTypeES.class);
        final Object target = getIdentifiable(update, targetEntity, update.getNewValue());
        update.getAttribute().getJavaMember().set(source, target);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(IdentifiableToEmbeddableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(source, update.getNewValue());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableSingularBasicAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        update.getUpdatedAttribute().getJavaMember().set(embeddable, update.getNewValue());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        final Object instance = getIdentifiableSource(update);
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateListOfNonIdentifiables(listMember, instance, update, update.getAdditions(), update.getRemovals());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)instance);
    }

    @Override
    public void visit(IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = metamodel.managedType(update.getAttribute().getJavaType(),IdentifiableTypeES.class);
        final MemberES listMember = update.getAttribute().getJavaMember();
        updateListOfIdentifiables(listMember, source, update, update.getAdditions(), update.getRemovals(), targetEntity);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateListOfNonIdentifiables(listMember, embeddable, update, update.getAdditions(), update.getRemovals());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(EmbeddableToEmbeddableSingularAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        update.getUpdatedAttribute().getJavaMember().set(embeddable, update.getNewValue());
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final IdentifiableTypeES targetEntity = metamodel.managedType(update.getUpdatedAttribute().getJavaType(),IdentifiableTypeES.class);
        final MemberES listMember = update.getUpdatedAttribute().getJavaMember();
        updateListOfIdentifiables(listMember, embeddable, update, update.getAdditions(), update.getRemovals(), targetEntity);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(EmbeddableToIdentifiableSingularAssociationAttributeUpdate update) {
        Object embeddable = getEmbeddable(update);
        final IdentifiableTypeES targetEntity = metamodel.managedType(update.getUpdatedAttribute().getJavaType(),IdentifiableTypeES.class);
        final Object target = getIdentifiable(update, targetEntity, update.getNewValue());
        update.getUpdatedAttribute().getJavaMember().set(embeddable, target);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)embeddable);
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES targetEntity = metamodel.managedType(mapAttribute.getElementType().getJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey().getValue())) {
                final Object value = getIdentifiable(update, targetEntity, (Serializable)removal.getValue().getValue());
                if (!map.remove(removal.getKey().getValue(), value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object value = getIdentifiable(update, targetEntity, (Serializable)addition.getValue().getValue());
            if(addition.getKey().getValue() == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(addition.getKey().getValue(), value);
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MemberES mapMember = update.getAttribute().getJavaMember();
        final Map map = mapMember.get(source);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey().getValue())) {
                if (!map.remove(removal.getKey().getValue(), removal.getValue().getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            if(addition.getKey().getValue() == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(addition.getKey().getValue(), addition.getValue().getValue());
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(IdentifiableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) throws IllegalChangeException {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES valueEntity = metamodel.managedType(mapAttribute.getElementType().getJavaType(),IdentifiableTypeES.class);
        final IdentifiableTypeES keyEntity = metamodel.managedType(mapAttribute.getKeyJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) removal.getKey().getValue());
            if (map.containsKey(key)) {
                final Object value = getIdentifiable(update, valueEntity, (Serializable)removal.getValue().getValue());
                if (!map.remove(key, value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) addition.getKey().getValue());
            final Object value = getIdentifiable(update, valueEntity, (Serializable) addition.getValue().getValue());
            if(key == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(key, value);
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(IdentifiableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES keyEntity = metamodel.managedType(mapAttribute.getKeyJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            //TODO possible change the method which retrieves an instance/identifiable
            final Object key = getIdentifiable(update, keyEntity,(Serializable) removal.getKey().getValue());
            if (map.containsKey(key)) {
                if (!map.remove(key, removal.getValue().getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) addition.getKey().getValue());
            if(key == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(key, addition.getValue().getValue());
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES valueEntity = metamodel.managedType(mapAttribute.getElementType().getJavaType(),IdentifiableTypeES.class);
        final IdentifiableTypeES keyEntity = metamodel.managedType(mapAttribute.getKeyJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) removal.getKey().getValue());
            if (map.containsKey(key)) {
                final Object value = getIdentifiable(update, valueEntity,(Serializable) removal.getValue().getValue());
                if (!map.remove(key, value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) addition.getKey().getValue());
            final Object value = getIdentifiable(update, valueEntity, (Serializable) addition.getValue().getValue());
            if(key == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(key, value);
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES keyEntity = metamodel.managedType(mapAttribute.getKeyJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) removal.getKey().getValue());
            if (map.containsKey(key)) {
                if (!map.remove(key, removal.getValue().getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object key = getIdentifiable(update, keyEntity, (Serializable) addition.getKey().getValue());
            if(key == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(key, addition.getValue().getValue());
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MapAttributeES mapAttribute = (MapAttributeES) update.getUpdatedAttribute();
        final MemberES mapMember = mapAttribute.getJavaMember();
        final Map map = mapMember.get(source);

        final IdentifiableTypeES targetEntity = metamodel.managedType(mapAttribute.getElementType().getJavaType(),IdentifiableTypeES.class);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey().getValue())) {
                final Object value = getIdentifiable(update, targetEntity, (Serializable)removal.getValue().getValue());
                if (!map.remove(removal.getKey().getValue(), value)) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            final Object value = getIdentifiable(update, targetEntity,(Serializable) addition.getValue().getValue());
            if(addition.getKey().getValue() == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(addition.getKey().getValue(), value);
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        final Object source = getEmbeddable(update);
        final MemberES mapMember = update.getUpdatedAttribute().getJavaMember();
        final Map map = mapMember.get(source);
        for (HasValueEntry removal : (List<HasValueEntry>) update.getRemovals()) {
            if (map.containsKey(removal.getKey().getValue())) {
                if (!map.remove(removal.getKey().getValue(), removal.getValue().getValue())) {
                    throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY_AND_VALUE);
                }
            } else {
                throw new IllegalChangeException(update, Reason.NO_ENTRY_FOR_KEY);
            }
        }
        for (HasValueEntry addition : (List<HasValueEntry>) update.getAdditions()) {
            if(addition.getKey() == null){
                throw new IllegalChangeException(update,Reason.KEY_IS_NULL);
            }
            map.put(addition.getKey().getValue(), addition.getValue().getValue());
        }
        mapMember.set(source, map);
        executionSummary.addCrudToChangeItem(CRUD.UPDATE, (Serializable)source);
    }
    
    @Override
    public ExecutionSummary getExecutionSummary(){
        return executionSummary;
    }
}
