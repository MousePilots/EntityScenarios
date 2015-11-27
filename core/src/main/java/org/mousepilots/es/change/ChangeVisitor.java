/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import org.mousepilots.es.change.impl.Create;
import org.mousepilots.es.change.impl.Delete;
import org.mousepilots.es.change.impl.EmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableSingularBasicAttributeUpdate;
import org.mousepilots.es.change.impl.JavaUtilMapAttributeUpdate;

/**
 *
 * @author Roy Cleven
 */
public interface ChangeVisitor {
    
    void visit(Create create);
    
    void visit(Delete delete);
    
    void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update);
    
    void visit(EmbeddableJavaUtilCollectionAssociationAttributeUpdate update);
    
    void visit(EmbeddableSingularAssociationAttributeUpdate update);
    
    void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update);
    
    void visit(IdentifiableSingularBasicAttributeUpdate update);
    
    void visit(IdentifiableJavaUtilCollectionAssociationAttributeUpdate update);
    
    void visit(IdentifiableSingularAssociationAttributeUpdate update);
    
    void visit(JavaUtilMapAttributeUpdate update);
}