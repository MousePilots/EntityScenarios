package org.mousepilots.es.server;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.mousepilots.es.change.Change;
import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.abst.AbstractIdentifiableVersionedChange;
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
import org.mousepilots.es.change.impl.IdentifiableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.JavaUtilMapAttributeUpdate;
import org.mousepilots.es.model.IdentifiableTypeES;
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
        final Collection list = listMember.get(instance);
        list.removeAll(update.getRemovals());
        list.addAll(update.getAdditions());
        listMember.set(instance, list);
    }

    @Override
    public void visit(IdentifiableSingularBasicAttributeUpdate update) {
        Object instance = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(instance, update.getNewValue());
    }

    @Override
    public void visit(JavaUtilMapAttributeUpdate update) {
        List<SimpleEntry> additions = update.getAdditions();
        List<SimpleEntry> removals = update.getRemovals();
        if (!additions.isEmpty()) {
            ListIterator<SimpleEntry> iterator = additions.listIterator();
            do {
                SimpleEntry entry = iterator.next();
            } while (iterator.hasNext());
        }
        if (!removals.isEmpty()) {
            ListIterator<SimpleEntry> iterator = removals.listIterator();
            do {
                SimpleEntry entry = iterator.next();
            } while (iterator.hasNext());
        }
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToIdentifiableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getAttribute().getJavaType());
        final Object target = getIdentifiable(update, targetEntity, update.getNewValue());
        update.getAttribute().getJavaMember().set(source, target);
    }

    @Override
    public void visit(IdentifiableToEmbeddableSingularAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        update.getAttribute().getJavaMember().set(source, update.getNewValue());
    }

    @Override
    public void visit(EmbeddableSingularBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        final Object source = getIdentifiableSource(update);
        final IdentifiableTypeES targetEntity = (IdentifiableTypeES) metamodel.managedType(update.getAttribute().getJavaType());
        final MemberES listMember = update.getAttribute().getJavaMember();
        final Collection list = listMember.get(source);
        for (Serializable removal : (List<Serializable>) update.getRemovals()) {
            final Object target = getIdentifiable(update, targetEntity, removal);
            list.remove(target);
        }
        for (Serializable addition : (List<Serializable>) update.getAdditions()) {
            final Object target = getIdentifiable(update, targetEntity, addition);
            list.add(target);
        }
        listMember.set(source, list);
    }

    @Override
    public void visit(EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToEmbeddableSingularAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToIdentifiableSingularAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
