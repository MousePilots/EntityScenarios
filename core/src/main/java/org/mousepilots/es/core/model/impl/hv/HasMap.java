/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;

/**
 *
 * @author jgeenen
 */
public final class HasMap implements HasValue<Map> {
    
    private LinkedHashMap<HasValue,HasValue> value;

    @Override
    public Map getValue() {
        if(value==null){
            return null;
        } else {
            final Map retval = new LinkedHashMap<>();
            for(Entry<HasValue,HasValue> entry : value.entrySet()){
                final Object k = entry.getKey().getValue();
                final Object v = entry.getValue().getValue();
                retval.put(k, v);
            }
            return retval;
        }
    }

    @Override
    public void setValue(Map map){
        if(map==null){
            this.value=null;
        } else {
            this.value = new LinkedHashMap<>();
            final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
            final Set<Entry> entrySet = map.entrySet();
            for(Entry entry : entrySet){
                final Object k = entry.getKey();
                final Object v = entry.getValue();
                final HasValue wrappedKey = metamodel.typeByInstance(k).wrap(k);
                final HasValue wrappedValue = metamodel.typeByInstance(v).wrap(v);
                this.value.put(wrappedKey, wrappedValue);
            }
        }
    }
}
