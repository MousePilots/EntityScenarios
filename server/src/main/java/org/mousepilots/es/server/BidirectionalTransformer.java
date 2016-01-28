package org.mousepilots.es.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.core.change.impl.IdentifiableToIdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.util.Transformer;

/**
 * @author Roy Cleven
 */
public class BidirectionalTransformer implements Transformer<Change, LinkedList<Change>>,HasEntityManager{
    
    
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LinkedList<Change> transform(LinkedList<Change> changes) {
        final Map<Integer, Collection<AbstractIdentifiableUpdate>> insertionPointToInverseUpdate = new TreeMap<>();

        //collect al inverse changes needed in insertionPointToInverseUpdate
        int index = 0;
        for (Change change : changes) {
            if (change instanceof AbstractIdentifiableUpdate) {
                AbstractIdentifiableUpdate update = (AbstractIdentifiableUpdate) change;
                final AssociationES association = update.getAttribute().getAssociation(AssociationTypeES.VALUE);
                if (association != null && association.isBiDirectional()) {
                    //check if inverse exists
                    if (!hasInverse(changes, insertionPointToInverseUpdate.values(), update)) {
                        //add a new inverted change
                        Collection<AbstractIdentifiableUpdate> inverseUpdates = null;
                        insertionPointToInverseUpdate.put(index + 1, inverseUpdates);
                    }
                }
            }
            index++;
        }

        //add all changes collected in insertionPointToInverseUpdate
        int added = 0;
        for (Entry<Integer, Collection<AbstractIdentifiableUpdate>> entry : insertionPointToInverseUpdate.entrySet()) {
            final Collection<AbstractIdentifiableUpdate> value = entry.getValue();
            changes.addAll(entry.getKey() + added, value);
            added += value.size();
        }

        return changes;
    }

    private Collection<AbstractIdentifiableUpdate> generateIdentifiableChange(AbstractIdentifiableUpdate update) {
        final AssociationES association = update.getAttribute().getAssociation(AssociationTypeES.KEY);
        final AssociationES inverse = association.getInverse();
        final AttributeES inverseAttribute = inverse.getSourceAttribute();
        final Collection<AbstractIdentifiableUpdate> changesNeededToAdd = new LinkedList<>();
        switch (inverse.getPersistentAttributeType()) {
            case MANY_TO_MANY:
                switch (inverseAttribute.getDeclaringType().getPersistenceType()) {
                    case ENTITY:
                        // change update into identifiabletoidentifiablecollection
                        IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate collectionChange = (IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) update;
                        for (Object instance : collectionChange.getAdditions()) {
                            changesNeededToAdd.add(generateIdentifableToIdentifiableCollectionUpdate(inverseAttribute, collectionChange, instance, update.getId()));
                        }
                    default:
                        throw new IllegalStateException();
                }
            case ONE_TO_MANY:
                switch (inverseAttribute.getDeclaringType().getPersistenceType()) {
                    case ENTITY:
                        // change update into identifiabletoidentifiablecollection
                        IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate collectionChange = (IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) update;
                        for (Object instance : collectionChange.getAdditions()) {
                            changesNeededToAdd.add(generateIdentifableToIdentifiableSingularUpdate(inverseAttribute, collectionChange, instance, update.getId()));
                        }
                    default:
                        throw new IllegalStateException();
                }
            case MANY_TO_ONE:
                switch (inverseAttribute.getDeclaringType().getPersistenceType()) {
                    case ENTITY:
                        // change update into identifiabletoidentifiablesingular
                        IdentifiableToIdentifiableSingularAssociationAttributeUpdate singularChange = (IdentifiableToIdentifiableSingularAssociationAttributeUpdate) update;
                        changesNeededToAdd.add(generateIdentifableToIdentifiableCollectionUpdate(inverseAttribute, singularChange, singularChange.getNewValue(), update.getId()));
                    default:
                        throw new IllegalStateException();
                }
            case ONE_TO_ONE:
                switch (inverseAttribute.getDeclaringType().getPersistenceType()) {
                    case ENTITY:
                        // change update into identifiabletoidentifiablesingular
                        IdentifiableToIdentifiableSingularAssociationAttributeUpdate singularChange = (IdentifiableToIdentifiableSingularAssociationAttributeUpdate) update;
                        changesNeededToAdd.add(generateIdentifableToIdentifiableSingularUpdate(inverseAttribute, singularChange, singularChange.getNewValue(), update.getId()));
                    default:
                        throw new IllegalStateException();
                }
        }
        return changesNeededToAdd;
    }

    //TODO: add missing values
    private AbstractIdentifiableUpdate generateIdentifableToIdentifiableSingularUpdate(final AttributeES inverseAttribute, AbstractIdentifiableUpdate update, Object inverseId, Serializable sourceId) {
        final IdentifiableTypeES declaringType = (IdentifiableTypeES) inverseAttribute.getDeclaringType();
        final SingularAttributeES version = declaringType.getVersion(null);
        final SingularAttributeES idAttribute = declaringType.getId(null);
        final HasValue id = idAttribute.wrapForChange(inverseId, update.getDtoType());
        AbstractIdentifiableUpdate newChange = new IdentifiableToIdentifiableSingularAssociationAttributeUpdate(inverseAttribute, null, id, declaringType, update.getDtoType(), null, sourceId);
        return newChange;
    }

    //TODO: add missing values
    private AbstractIdentifiableUpdate generateIdentifableToIdentifiableCollectionUpdate(final AttributeES inverseAttribute, AbstractIdentifiableUpdate update, Object inverseId, Serializable sourceId) {
        final IdentifiableTypeES declaringType = (IdentifiableTypeES) inverseAttribute.getDeclaringType();
        final SingularAttributeES version = declaringType.getVersion(null);
        final SingularAttributeES idAttribute = declaringType.getId(null);
        final HasValue id = idAttribute.wrapForChange(inverseId, update.getDtoType());
        List<Serializable> addition = new ArrayList<>();
        addition.add(sourceId);
        AbstractIdentifiableUpdate newChange = new IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate(inverseAttribute, null, id, declaringType, update.getDtoType(), null, addition);
        return newChange;
    }

    /**
     * 
     * @param changes
     * @param values
     * @param update
     * @return 
     */
    private boolean hasInverse(LinkedList<Change> changes, Collection<Collection<AbstractIdentifiableUpdate>> values, AbstractIdentifiableUpdate update) {
        for (Collection<AbstractIdentifiableUpdate> addedInverseChanges : values) {
            for (AbstractIdentifiableUpdate change : addedInverseChanges) {
                if (checkIfInverse(change, update)) {
                    return true;
                }
            }
        }
        for (Change change : changes) {
            if (change instanceof AbstractIdentifiableUpdate && checkIfInverse((AbstractIdentifiableUpdate) change, update)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given Update is the change for the inverse of relation which the Change changes. 
     * @param change Change which changes a bi-directional relation
     * @param update Possible change which changes the inverse of this relation
     * @return True if the Update changes the inverse of the relation.
     */
    private boolean checkIfInverse(AbstractIdentifiableUpdate change, AbstractIdentifiableUpdate update) {
        if (change instanceof IdentifiableToIdentifiableSingularAssociationAttributeUpdate) {
            IdentifiableToIdentifiableSingularAssociationAttributeUpdate inverse = (IdentifiableToIdentifiableSingularAssociationAttributeUpdate) change;
            if (update instanceof IdentifiableToIdentifiableSingularAssociationAttributeUpdate) {
                IdentifiableToIdentifiableSingularAssociationAttributeUpdate source = (IdentifiableToIdentifiableSingularAssociationAttributeUpdate) update;
                if (Objects.equals(source.getId(), inverse.getNewValue()) && Objects.equals(source.getNewValue(), inverse.getId())) {
                    return true;
                }
            } else if (update instanceof IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) {
                IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate source = (IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) update;
                for (Object additionId : source.getAdditions()) {
                    if (Objects.equals(source.getId(), inverse.getNewValue()) && Objects.equals(additionId, inverse.getId())) {
                        return true;
                    }
                }
            }
        } else if (change instanceof IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) {
            IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate inverse = (IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) change;
            for (Object inverseAdditionId : inverse.getAdditions()) {
                if (update instanceof IdentifiableToIdentifiableSingularAssociationAttributeUpdate) {
                    IdentifiableToIdentifiableSingularAssociationAttributeUpdate source = (IdentifiableToIdentifiableSingularAssociationAttributeUpdate) update;
                    if (Objects.equals(source.getId(), inverseAdditionId) && Objects.equals(source.getNewValue(), inverse.getId())) {
                        return true;
                    }
                } else if (update instanceof IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) {
                    IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate source = (IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate) update;
                    for (Object additionId : source.getAdditions()) {
                        if (Objects.equals(source.getId(), inverseAdditionId) && Objects.equals(additionId, inverse.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
