package org.mousepilots.es.core.change.impl.transform;

import java.util.LinkedList;
import java.util.Objects;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.change.abst.AbstractIdentifiableVersionedChange;
import org.mousepilots.es.core.change.impl.*;
import org.mousepilots.es.core.util.Transformer;

/**
 * @author Roy Cleven
 */
public class DuplicateTransformer implements Transformer<Change, LinkedList<Change>> {

    @Override
    public LinkedList<Change> transform(LinkedList<Change> changes) {
        LinkedList<Change> changesToRemove = new LinkedList<>();
        for (Change change : changes) {
            if(change instanceof Delete){
                Delete delete = (Delete) change;
                for (Change changeToRemove : changesToRemove) {
                    if(changeToRemove instanceof AbstractIdentifiableVersionedChange){
                        AbstractIdentifiableVersionedChange abstractChange = (AbstractIdentifiableVersionedChange) changeToRemove;
                        if(Objects.equals(delete.getId(), abstractChange.getId())&&
                                Objects.equals(delete.getVersion(), abstractChange.getVersion()) &&
                                delete.getType().equals(abstractChange.getType())){
                            changesToRemove.add(abstractChange);
                        }
                    }
                }
            }
            //Singular change checks
            else if (change instanceof EmbeddableSingularBasicAttributeUpdate) {
                EmbeddableSingularBasicAttributeUpdate singularBasic = (EmbeddableSingularBasicAttributeUpdate) change;
                for (Change possibleDuplicate : changes) {
                    if (possibleDuplicate instanceof EmbeddableSingularBasicAttributeUpdate) {
                        EmbeddableSingularBasicAttributeUpdate duplicate = (EmbeddableSingularBasicAttributeUpdate) possibleDuplicate;
                        if (Objects.equals(singularBasic.getContainerId(), duplicate.getContainerId())
                                && Objects.equals(singularBasic.getContainer(), duplicate.getContainer())
                                && Objects.equals(singularBasic.getUpdated(), duplicate.getUpdated())
                                && singularBasic.getContainerAttribute().equals(duplicate.getContainerAttribute())
                                && Objects.equals(singularBasic.getOldValue(), duplicate.getNewValue())) {
                            changesToRemove.add(possibleDuplicate);
                        }
                    }
                }
            } else if (change instanceof EmbeddableSingularAssociationAttributeUpdate) {
                EmbeddableSingularAssociationAttributeUpdate embeddableAssociation = (EmbeddableSingularAssociationAttributeUpdate) change;
                for (Change possibleDuplicate : changes) {
                    if (possibleDuplicate instanceof EmbeddableSingularAssociationAttributeUpdate) {
                        EmbeddableSingularAssociationAttributeUpdate duplicate = (EmbeddableSingularAssociationAttributeUpdate) possibleDuplicate;
                        if (Objects.equals(embeddableAssociation.getContainerId(), duplicate.getContainerId())
                                && Objects.equals(embeddableAssociation.getContainer(), duplicate.getContainer())
                                && Objects.equals(embeddableAssociation.getUpdated(), duplicate.getUpdated())
                                && embeddableAssociation.getContainerAttribute().equals(duplicate.getContainerAttribute())
                                && Objects.equals(embeddableAssociation.getOldValue(), duplicate.getNewValue())) {
                            changesToRemove.add(possibleDuplicate);
                        }
                    }
                }
            } else if (change instanceof IdentifiableSingularBasicAttributeUpdate) {
                IdentifiableSingularBasicAttributeUpdate identifiableBasic = (IdentifiableSingularBasicAttributeUpdate) change;
                for (Change possibleDuplicate : changes) {
                    if (possibleDuplicate instanceof IdentifiableSingularBasicAttributeUpdate) {
                        IdentifiableSingularBasicAttributeUpdate duplicate = (IdentifiableSingularBasicAttributeUpdate) possibleDuplicate;
                        if (Objects.equals(identifiableBasic.getId(), duplicate.getId())
                                && Objects.equals(identifiableBasic.getVersion(), duplicate.getVersion())
                                && identifiableBasic.getType().equals(duplicate.getType())
                                && identifiableBasic.getAttribute().equals(duplicate.getAttribute())
                                && Objects.equals(identifiableBasic.getOldValue(), duplicate.getNewValue())) {

                        }
                    }
                }
            } else if (change instanceof IdentifiableSingularAssociationAttributeUpdate) {
                IdentifiableSingularAssociationAttributeUpdate identifiableAssociation = (IdentifiableSingularAssociationAttributeUpdate) change;
                for (Change possibleDuplicate : changes) {
                    if (possibleDuplicate instanceof IdentifiableSingularAssociationAttributeUpdate) {
                        IdentifiableSingularAssociationAttributeUpdate duplicate = (IdentifiableSingularAssociationAttributeUpdate) possibleDuplicate;
                        if (Objects.equals(identifiableAssociation.getId(), duplicate.getId())
                                && Objects.equals(identifiableAssociation.getVersion(), duplicate.getVersion())
                                && identifiableAssociation.getType().equals(duplicate.getType())
                                && identifiableAssociation.getAttribute().equals(duplicate.getAttribute())
                                && Objects.equals(identifiableAssociation.getOldValue(), duplicate.getNewValue())) {

                        }
                    }
                }
            } // Collecitons
            else if (change instanceof EmbeddableJavaUtilCollectionBasicAttributeUpdate) {

            } else if (change instanceof EmbeddableJavaUtilCollectionAssociationAttributeUpdate) {

            } else if (change instanceof IdentifiableJavaUtilCollectionBasicAttributeUpdate) {

            } else if (change instanceof IdentifiableJavaUtilCollectionAssociationAttributeUpdate) {

            } //Maps
            else if (change instanceof EmbeddableJavaUtilMapAttributeUpdate) {

            } else if (change instanceof IdentifiableJavaUtilMapAttributeUpdate) {

            }
        }
        for (Change changeToRemove : changesToRemove) {
            if(changes.contains(changeToRemove)){
                changes.remove(changeToRemove);
            }
        }
        return changes;
    }
}
