/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;

/**
 *
 * @author jgeenen
 */
abstract class AbstractHasCollection<C extends Collection> extends AbstractHasValue<C>{
    
    private ArrayList<HasValue> hasValues;
    
    
    protected final List createEmptyList(){
        return hasValues==null ? new LinkedList<>() : new ArrayList<>(hasValues.size());
    }
    
    protected abstract C createEmpty();


    @Override
    public final void setValue(C values) {
        final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
        hasValues = new ArrayList<>(values.size());
        for(Object value : values){
            if(value==null){
                hasValues.add(null);
            } else {
                final TypeES type = metamodel.typeByInstance(value);
                hasValues.add(type.wrap(value));
            }
        }
    }

    
    
    @Override
    public final C getValue(){
        if(hasValues==null){
            return null;
        } else {
            final C retval = createEmpty();
            for(HasValue hv : hasValues){
                retval.add(hv.getValue());
            }
            return retval;
        }
    }
    
}
