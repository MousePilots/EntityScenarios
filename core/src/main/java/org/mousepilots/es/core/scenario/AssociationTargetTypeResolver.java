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
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;

/**
 *
 * @author jgeenen
 */
public class AssociationTargetTypeResolver implements AttributeVisitor<ManagedTypeES,AssociationTypeES>{
    
    private void assertIsValue(AssociationTypeES associationTypeES){
        if(associationTypeES==AssociationTypeES.KEY){
            throw new IllegalStateException("bad associationType " + associationTypeES);
        }
    }

    
    @Override
    public ManagedTypeES visit(SingularAttributeES a,AssociationTypeES associationTypeES) {
        assertIsValue(associationTypeES);
        return (ManagedTypeES) a.getType();
    }

    @Override
    public ManagedTypeES visit(CollectionAttributeES a, AssociationTypeES associationTypeES) {
        assertIsValue(associationTypeES);
        return (ManagedTypeES) a.getElementType();
    }

    @Override
    public ManagedTypeES visit(ListAttributeES a, AssociationTypeES associationTypeES) {
        assertIsValue(associationTypeES);
        return (ManagedTypeES) a.getElementType();
    }

    @Override
    public ManagedTypeES visit(SetAttributeES a, AssociationTypeES associationTypeES) {
        assertIsValue(associationTypeES);
        return (ManagedTypeES) a.getElementType();
    }

    @Override
    public ManagedTypeES visit(MapAttributeES a, AssociationTypeES associationTypeES){
        return (ManagedTypeESImpl)(
                associationTypeES==AssociationTypeES.KEY ? 
                a.getKeyType() : 
                a.getElementType()
        );
    }

}
