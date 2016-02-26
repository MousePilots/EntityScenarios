/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import java.util.EnumMap;
import java.util.Map;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.core.change.Change;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.collections.ObservableCollection;
import org.mousepilots.es.core.model.collections.ObservableList;
import org.mousepilots.es.core.model.collections.ObservableMap;
import org.mousepilots.es.core.model.collections.ObservableSet;
import org.mousepilots.es.core.model.impl.Constructor;

/**
 *
 * @author geenenju
 */
class AttributeSetter<X,Y> implements AttributeVisitor<Change, SetRequest<X,Y>>{
     
     @Override
     public Change visit(SingularAttributeES a, SetRequest<X, Y> request){
          Change update = null;
          if(request.getProxyAspect().isManagedMode()){
               //setting
               if(a.isId() || a.isVersion()){
                    throw new UnsupportedOperationException(
                         "cannot set Id or Version attribute " + a + " on " + request.getProxy() + " in managed mode"
                    );
               }
               switch(request.getProxyType().getPersistenceType()){
               }
          } else {
               request.getSuperSetter().apply(request.getNewAttributeValue());
          }
          return update;
     }

     private Change visitPluralAttribute(PluralAttributeES a, Constructor emptyPluralAttributeValue,SetRequest<X, Y> request) throws UnsupportedOperationException {
          if(request.getProxyAspect().isManagedMode()){
               throw new UnsupportedOperationException("Setting of collection attribute " + a + " is not supported in managed mode. Modify the collection instead.");
          } else {
               Y pluralAttributeValue = request.getNewAttributeValue();
               if(pluralAttributeValue==null){
                    pluralAttributeValue = (Y) emptyPluralAttributeValue.invoke();
               }
               request.getSuperSetter().apply(pluralAttributeValue);
               return null;
          }
     }

     @Override
     public Change visit(CollectionAttributeES a, SetRequest<X, Y> request) {
          return visitPluralAttribute(a,ObservableCollection::new,request);
     }


     @Override
     public Change visit(ListAttributeES a, SetRequest<X, Y> request) {
          return visitPluralAttribute(a,ObservableList::new,request);
     }

     @Override
     public Change visit(SetAttributeES a, SetRequest<X, Y> request) {
          return visitPluralAttribute(a,ObservableSet::new,request);
     }

     @Override
     public Change visit(MapAttributeES a, SetRequest<X, Y> request) {
          return visitPluralAttribute(a,ObservableMap::new,request);
     }
}
