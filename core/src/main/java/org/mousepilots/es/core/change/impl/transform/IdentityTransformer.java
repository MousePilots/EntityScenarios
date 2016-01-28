package org.mousepilots.es.core.change.impl.transform;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.change.ChangeVisitor;
import org.mousepilots.es.core.change.ExecutionSummary;
import org.mousepilots.es.core.change.impl.*;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.util.Transformer;

/**
 * @author Roy Cleven
 *
 */
public class IdentityTransformer implements Transformer<Change, LinkedList<Change>>, ChangeVisitor {

    LinkedList<Change> changes;
    LinkedList<Change> changesToRemove;
    LinkedList<Change> changesToAdd;

    @Override
    public LinkedList<Change> transform(LinkedList<Change> changes) {
        LinkedList<Change> changesToRemove = new LinkedList<>();
        LinkedList<Change> changesCopy = new LinkedList<>(changes);
        while(changesCopy.size() > 0){
            changesCopy.removeLast().accept(this);
        }
        for (Change changeToRemove : changesToRemove) {
            if (changes.contains(changeToRemove)) {
                changes.remove(changeToRemove);
            }
        }
        for (Change changeToRemove : changesToAdd) {
            if (!changes.contains(changeToRemove)) {
                changes.add(changeToRemove);
            }
        }
        return changes;
    }

    @Override
    public void visit(Delete delete) {
        if (!changesToRemove.contains(delete)) {
            for (Change change : changes) {
                if (change instanceof Create) {
                    Create create = (Create) change;
                    if (Objects.equals(delete.getId(), create.getId())
                            && delete.getType().equals(create.getType())) {
                        changesToRemove.add(create);
                        changesToRemove.add(delete);
                    }
                }
            }
        }
    }

    @Override
    public void visit(Create create) {
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    EmbeddableJavaUtilCollectionBasicAttributeUpdate duplicate = (EmbeddableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && update.getUpdatedAttribute().equals(duplicate.getUpdatedAttribute())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && update.getContainerEmbeddables().equals(duplicate.getContainerEmbeddables())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                EmbeddableJavaUtilCollectionBasicAttributeUpdate newChange = new EmbeddableJavaUtilCollectionBasicAttributeUpdate(update.getContainer(), update.getUpdated(), update.getContainerAttribute().wrapForChange(update.getContainerId(), update.getDtoType()), update.getContainerAttribute(), update.getUpdatedAttribute(), update.getType(), update.getDtoType(), allAdditions, allRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    EmbeddableJavaUtilCollectionBasicAttributeUpdate duplicate = (EmbeddableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && update.getUpdatedAttribute().equals(duplicate.getUpdatedAttribute())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && update.getContainerEmbeddables().equals(duplicate.getContainerEmbeddables())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                EmbeddableJavaUtilCollectionBasicAttributeUpdate newChange = new EmbeddableJavaUtilCollectionBasicAttributeUpdate(update.getContainer(), update.getUpdated(), update.getContainerAttribute().wrapForChange(update.getContainerId(), update.getDtoType()), update.getContainerAttribute(), update.getUpdatedAttribute(), update.getType(), update.getDtoType(), allAdditions, allRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(EmbeddableToEmbeddableSingularAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof EmbeddableSingularAssociationAttributeUpdate) {
                    EmbeddableSingularAssociationAttributeUpdate duplicate = (EmbeddableSingularAssociationAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && Objects.equals(update.getContainer(), duplicate.getContainer())
                            && Objects.equals(update.getUpdated(), duplicate.getUpdated())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    EmbeddableJavaUtilCollectionBasicAttributeUpdate duplicate = (EmbeddableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && update.getUpdatedAttribute().equals(duplicate.getUpdatedAttribute())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && update.getContainerEmbeddables().equals(duplicate.getContainerEmbeddables())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                EmbeddableJavaUtilCollectionBasicAttributeUpdate newChange = new EmbeddableJavaUtilCollectionBasicAttributeUpdate(update.getContainer(), update.getUpdated(), update.getContainerAttribute().wrapForChange(update.getContainerId(), update.getDtoType()), update.getContainerAttribute(), update.getUpdatedAttribute(), update.getType(), update.getDtoType(), allAdditions, allRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(EmbeddableToIdentifiableSingularAssociationAttributeUpdate update) {
        //TODO bidirectional check
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof EmbeddableSingularAssociationAttributeUpdate) {
                    EmbeddableSingularAssociationAttributeUpdate duplicate = (EmbeddableSingularAssociationAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && Objects.equals(update.getContainer(), duplicate.getContainer())
                            && Objects.equals(update.getUpdated(), duplicate.getUpdated())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(EmbeddableSingularBasicAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof EmbeddableSingularBasicAttributeUpdate) {
                    EmbeddableSingularBasicAttributeUpdate duplicate = (EmbeddableSingularBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getContainerId(), duplicate.getContainerId())
                            && Objects.equals(update.getContainer(), duplicate.getContainer())
                            && Objects.equals(update.getUpdated(), duplicate.getUpdated())
                            && update.getContainerAttribute().equals(duplicate.getContainerAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    IdentifiableJavaUtilCollectionBasicAttributeUpdate duplicate = (IdentifiableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                IdentifiableJavaUtilCollectionBasicAttributeUpdate newChange = new IdentifiableJavaUtilCollectionBasicAttributeUpdate(update.getAttribute(), update.getVersion(), update.getAttribute().wrapForChange(update.getId(), update.getDtoType()), (IdentifiableTypeES) update.getType(), update.getDtoType(), allActualAdditions, allActualRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(IdentifiableSingularBasicAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableSingularBasicAttributeUpdate) {
                    IdentifiableSingularBasicAttributeUpdate duplicate = (IdentifiableSingularBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getType().equals(duplicate.getType())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(IdentifiableToEmbeddableSingularAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableSingularAssociationAttributeUpdate) {
                    IdentifiableSingularAssociationAttributeUpdate duplicate = (IdentifiableSingularAssociationAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getType().equals(duplicate.getType())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(IdentifiableToIdentifiableSingularAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableSingularAssociationAttributeUpdate) {
                    IdentifiableSingularAssociationAttributeUpdate duplicate = (IdentifiableSingularAssociationAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getType().equals(duplicate.getType())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && Objects.equals(update.getOldValue(), duplicate.getNewValue())) {
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
        }
    }

    @Override
    public void visit(IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    IdentifiableJavaUtilCollectionBasicAttributeUpdate duplicate = (IdentifiableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                IdentifiableJavaUtilCollectionBasicAttributeUpdate newChange = new IdentifiableJavaUtilCollectionBasicAttributeUpdate(update.getAttribute(), update.getVersion(), update.getAttribute().wrapForChange(update.getId(), update.getDtoType()), (IdentifiableTypeES) update.getType(), update.getDtoType(), allActualAdditions, allActualRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        if (!changesToRemove.contains(update)) {
            List<Object> allAdditions = update.getAdditions();
            List<Object> allRemovals = update.getRemovals();
            for (Change possibleDuplicate : changes) {
                if (possibleDuplicate instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {
                    IdentifiableJavaUtilCollectionBasicAttributeUpdate duplicate = (IdentifiableJavaUtilCollectionBasicAttributeUpdate) possibleDuplicate;
                    if (Objects.equals(update.getId(), duplicate.getId())
                            && Objects.equals(update.getVersion(), duplicate.getVersion())
                            && update.getAttribute().equals(duplicate.getAttribute())
                            && update.getType().equals(duplicate.getType())) {
                        allAdditions.addAll(duplicate.getAdditions());
                        allRemovals.addAll(duplicate.getRemovals());
                        changesToRemove.add(possibleDuplicate);
                    }
                }
            }
            List<Object> allActualAdditions = new LinkedList<>();
            List<Object> allActualRemovals = new LinkedList<>();
            if (allAdditions.size() > update.getAdditions().size() || allRemovals.size() > update.getRemovals().size()) {
                for (Object addition : allAdditions) {
                    if (!allRemovals.contains(addition)) {
                        allActualAdditions.add(addition);
                    }
                }
                for (Object removal : allRemovals) {
                    if (!allAdditions.contains(removal)) {
                        allActualRemovals.add(removal);
                    }
                }
                //create new change containing all the actual changes
                IdentifiableJavaUtilCollectionBasicAttributeUpdate newChange = new IdentifiableJavaUtilCollectionBasicAttributeUpdate(update.getAttribute(), update.getVersion(), update.getAttribute().wrapForChange(update.getId(), update.getDtoType()), (IdentifiableTypeES) update.getType(), update.getDtoType(), allActualAdditions, allActualRemovals);
                changesToAdd.add(newChange);
                changesToRemove.add(update);
            }
        }
    }

    @Override
    public void visit(IdentifiableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableToNonIdentifiableToNonIdentifiableJavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExecutionSummary getExecutionSummary() {
        return null;
    }
}
