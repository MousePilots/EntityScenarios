/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

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

/**
 *
 * @author Roy Cleven
 */
public interface ChangeVisitor {

    void visit(Create create);

    void visit(Delete delete);

    void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update);

    void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update);

    void visit(IdentifiableSingularBasicAttributeUpdate update);

    void visit(IdentifiableToEmbeddableSingularAssociationAttributeUpdate update);

    void visit(IdentifiableToIdentifiableSingularAssociationAttributeUpdate update);

    void visit(EmbeddableSingularBasicAttributeUpdate update);

    void visit(IdentifiableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update);

    void visit(IdentifiableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update);

    void visit(EmbeddableToEmbeddableJavaUtilCollectionAssociationAttributeUpdate update);

    void visit(EmbeddableToEmbeddableSingularAssociationAttributeUpdate update);

    void visit(EmbeddableToIdentifiableJavaUtilCollectionAssociationAttributeUpdate update);

    void visit(EmbeddableToIdentifiableSingularAssociationAttributeUpdate update);

    void visit(JavaUtilMapAttributeUpdate update);
}
