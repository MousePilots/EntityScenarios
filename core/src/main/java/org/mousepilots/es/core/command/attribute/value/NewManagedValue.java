/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import org.mousepilots.es.core.command.Create;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <TD>
 */
public final class NewManagedValue<E,TD extends ManagedTypeES<E>> extends Value<Proxy<E>,E,Create<E,? extends ManagedTypeES<E>>, ManagedTypeES<E>> {

     private Create<E,? extends ManagedTypeES<E>> encodedServerValue;
     
     private NewManagedValue(){}

     @Override @GwtIncompatible
     protected E decode(Create<E,? extends ManagedTypeES<E>> encodedServerValue, ServerContext serverContext) {
          return encodedServerValue.getRealSubject();
     }

     @Override
     protected Create<E, ? extends ManagedTypeES<E>> getEncodedServerValue() {
          return encodedServerValue;
     }
     
     private NewManagedValue(Proxy<E> proxy, ProxyAspect<E> proxyAspect){
          super(proxyAspect.getTypeOrdinal(),proxy);
          encodedServerValue = proxyAspect.getCreate();
     }
     
     NewManagedValue(Proxy<E> proxy) {
          this(proxy,proxy.__getProxyAspect());
     }
     
     @Override
     public <R, A> R accept(ValueVisitor<R, A> valueVisitor, A arg) {
          return valueVisitor.visit(this, arg);
     }
     

     
}
