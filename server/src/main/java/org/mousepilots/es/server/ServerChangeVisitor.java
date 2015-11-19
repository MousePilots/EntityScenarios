package org.mousepilots.es.server;

import org.mousepilots.es.change.ChangeVisitor;
import org.mousepilots.es.change.impl.Create;
import org.mousepilots.es.change.impl.Delete;
import org.mousepilots.es.change.impl.EmbeddableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.EmbeddableSingularBasicAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableJavaUtilCollectionAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableJavaUtilCollectionBasicAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableSingularAssociationAttributeUpdate;
import org.mousepilots.es.change.impl.IdentifiableSingularBasicAttributeUpdate;
import org.mousepilots.es.change.impl.JavaUtilMapAttributeUpdate;



/**
 * @author Roy Cleven
 */
public class ServerChangeVisitor implements ChangeVisitor{

    @Override
    public void visit(Create create) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Delete delete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableJavaUtilCollectionBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableSingularAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EmbeddableSingularBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableJavaUtilCollectionAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableJavaUtilCollectionBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableSingularAssociationAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IdentifiableSingularBasicAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(JavaUtilMapAttributeUpdate update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}