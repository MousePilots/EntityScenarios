/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;

/**
 *
 * @author jgeenen
 */
public class AssociationTargetTypeResolver implements AttributeVisitor{
    
    private final AssociationTypeES associationTypeES;
    
    private ManagedTypeES associationTargetType;
    
    public AssociationTargetTypeResolver(AssociationTypeES associationTypeES) {
        this.associationTypeES = associationTypeES;
    }
    
    private void assertIsValue(){
        if(associationTypeES==AssociationTypeES.KEY){
            throw new IllegalStateException("bad associationType " + associationTypeES);
        }
    }

    public ManagedTypeES getAssociationTargetType() {
        return associationTargetType;
    }
    
    @Override
    public void visit(SingularAttributeES a) {
        assertIsValue();
        associationTargetType = (ManagedTypeES) a.getType();
    }

    @Override
    public void visit(CollectionAttributeES a) {
        assertIsValue();
        associationTargetType = (ManagedTypeES) a.getElementType();
    }

    @Override
    public void visit(ListAttributeES a) {
        assertIsValue();
        associationTargetType = (ManagedTypeES) a.getElementType();
    }

    @Override
    public void visit(SetAttributeES a) {
        assertIsValue();
        associationTargetType = (ManagedTypeES) a.getElementType();
    }

    @Override
    public void visit(MapAttributeES a) {
        associationTargetType = (ManagedTypeES) (
            associationTypeES==AssociationTypeES.KEY ? a.getKeyType() : a.getElementType()
        );
    }

}
