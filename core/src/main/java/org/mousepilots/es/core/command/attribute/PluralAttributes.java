/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.OwnedGetter;
import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.PluralAttributeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.Observable;
import org.mousepilots.es.core.util.Framework;

@Framework
public class PluralAttributes {
    
    private PluralAttributes(){}
    
    private static <E, C> PluralAttributeESImpl<E, C, ?> getAttribute(int pluralAttributeOrdinal) {
        return (PluralAttributeESImpl<E,C,?>) AbstractMetamodelES.getInstance().getAttribute(pluralAttributeOrdinal);
    }
    
    private static <E,C> C createObservable(final PluralAttributeESImpl<E, C, ?> attribute, Proxy<E> proxy, final C valueToObserve, OwnedSetter<C> superSetter) {
        final C observedValue = attribute.getObserved(proxy, valueToObserve);
        superSetter.set(observedValue);
        return observedValue;
    }

    public static <E,C> C get(Proxy<E> proxy, int pluralAttributeOrdinal, OwnedGetter<C> superGetter, OwnedSetter<C> superSetter){
        final PluralAttributeESImpl<E,C,?> attribute = getAttribute(pluralAttributeOrdinal);
        final C value = superGetter.get();
        if(value!=null && value instanceof Observable){
            return value;
        } else {
            final C valueToObserve = value==null ? attribute.createEmpty() : value;
            return createObservable(attribute, proxy, valueToObserve, superSetter);
        }
    }

    
    public static <E,C> void set(Proxy<E> proxy, int pluralAttributeOrdinal, OwnedSetter<C> superSetter, C value){
        if(proxy.__getProxyAspect().isManagedMode()){
            throw new UnsupportedOperationException("setting plural attributes on proxies is unsupported in managed mode");
        } 
        if(value instanceof Observable){
            throw new UnsupportedOperationException("directly setting instances of " + Observable.class + " is unsupported");
        }
        if(value==null){
            value = PluralAttributes.<E,C>getAttribute(pluralAttributeOrdinal).createEmpty();
        }
        superSetter.set(createObservable(getAttribute(pluralAttributeOrdinal), proxy, value, superSetter));
    }
    
}
