/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.Constructor;
import org.mousepilots.es.core.scenario.Arc;
import org.mousepilots.es.core.scenario.Vertex;

/**
 * Serializes {@link #getValueToSerialize()} for its {@link #getParent()}. The implementations uses the 
 * visitor pattern provided by {@link AttributeVisitor} and {@link AttributeES#accept(org.mousepilots.es.core.model.AttributeVisitor)}.
 * Note that  {@link #getValueToSerialize()} may be re-set in recursion. Hence {@code visit(...)} method bodies must store this value in a local variable.
 * @author jgeenen
 * @param <S> the {@link Serializer}-type {@code this} is a delegate for
 */
public class AttributeSerializerDelegate<S extends Serializer> extends SerializerDelegate<S> implements AttributeVisitor<Object,Object>{

    public AttributeSerializerDelegate(S serializer) {
        super(serializer);
    }
    
        @Override
        public Object visit(SingularAttributeES a, Object value) {
            if(value==null){
                return null;
            } else {
                if(a.isAssociation()){
                    return null;
                } else {
                    return value;
                }
            }
        }
        
        protected <C extends Collection> C visitCollection(PluralAttributeES a, Constructor<? extends C> collectionConstructor,Collection collectionValue){
            if(collectionValue==null){
                return null;
            } else {
                final AssociationES association = a.getAssociation(AssociationTypeES.VALUE);
                if(association!=null){
                    final S serializer = getParent();
                    final Arc arc = serializer.getScenarioGraph().getArc(association);
                    if(arc==null){
                        return null;
                    } else {
                        C retval = null;
                        final Vertex target = arc.getTarget();
                        if(target.getTypeAuthorizationStatusBeforeProcessing(CRUD.READ,serializer.getContext())){
                            retval = collectionConstructor.invoke();
                            final S parent = getParent();
                            for(Object o : collectionValue){
                                retval.add(parent.serializeManagedTypeValue(o));
                            }
                        }
                        return retval;
                    }
                } else {
                    final C retval = collectionConstructor.invoke();
                    retval.addAll(collectionValue);
                    return retval;
                }
            }
        }

        @Override
        public Object visit(CollectionAttributeES a, Object collectionValue) {
            return visitCollection(a, ArrayList::new, (Collection) collectionValue);
        }

        @Override
        public Object visit(ListAttributeES a, Object collectionValue) {
            return visitCollection(a, ArrayList::new, (List) collectionValue);
            
        }

        @Override
        public Object visit(SetAttributeES a, Object setValue) {
            return visitCollection(a, HashSet::new, (Set) setValue);
        }

        @Override
        public Object visit(MapAttributeES a, Object value) {
            final Map mapValue = (Map) value;
            if(mapValue==null){
                return null;
            } else {
                final AssociationES ka = a.getAssociation(AssociationTypeES.KEY);
                final AssociationES va = a.getAssociation(AssociationTypeES.VALUE);
                if(ka==null && va==null){
                    return new HashMap<>(mapValue);
                } else {
                    final Map retval = new HashMap();
                    final Set<Entry> entrySet = mapValue.entrySet();
                    final S parent = getParent();
                    for(Entry e : entrySet){
                        final Object k = ka==null ? e.getKey()  : parent.serializeManagedTypeValue(e.getKey());
                        final Object v = va==null ? e.getValue(): parent.serializeManagedTypeValue(e.getValue());
                        retval.put(k, v);
                    }
                    return retval;
                }
            }
        }
    
}
