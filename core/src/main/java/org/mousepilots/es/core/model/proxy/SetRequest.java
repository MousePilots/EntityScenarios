/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.util.Procedure;

class SetRequest<X,Y> {
     
     private final Proxy<X> proxy;
     
     private AttributeESImpl<? super X,Y> attribute;
     
     private final Procedure<Y> superSetter;
     
     private final Y newAttributeValue;
     
     private final ProxyAspect proxyAspect;
     
     public SetRequest(Proxy<X> proxy, AttributeESImpl<? super X,Y> attribute, Procedure<Y> superSetter, Y newAttributeValue) {
          this.proxy = proxy;
          this.attribute = attribute;
          this.superSetter=superSetter;
          this.newAttributeValue = newAttributeValue;
          this.proxyAspect = proxy.__getProxyAspect();
     }

     public Proxy<X> getProxy() {
          return proxy;
     }

     public AttributeESImpl<? super X, Y> getAttribute() {
          return attribute;
     }

     public Procedure<Y> getSuperSetter() {
          return superSetter;
     }

     public Y getCurrentAttributeValue(){
          return attribute.getJavaMember().get(proxy);
     }
     
     public Y getNewAttributeValue() {
          return newAttributeValue;
     }

     public ProxyAspect getProxyAspect() {
          return proxyAspect;
     }

     public ManagedTypeESImpl<X> getProxyType() {
          return proxyAspect.getType();
     }

     public boolean isChangeGenerationEnabled() {
          return proxyAspect.isManagedMode();
     }
     
     
}
