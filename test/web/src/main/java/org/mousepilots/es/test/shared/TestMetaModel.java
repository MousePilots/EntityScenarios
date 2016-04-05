/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.util.Map;
import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.test.domain.MetamodelImpl;

/**
 *
 * @author jgeenen
 */
public class TestMetaModel {
    
    static {
        MetamodelImpl.init();
    }
    
    public void testAssociations(){
        final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
        for(ManagedType t : metamodel.getManagedTypes()){
            ManagedTypeES managedTypeES = (ManagedTypeES) t;
            managedTypeES.getAttributes().forEach(a -> {
                AttributeES attributeES = (AttributeES) a;
                final TypeES type = attributeES.getType();
                if(type instanceof ManagedTypeES){
                    
                }
                final Map<AssociationTypeES, AssociationES> associations = attributeES.getAssociations();

            
            
            });
        }
    }
    
}
