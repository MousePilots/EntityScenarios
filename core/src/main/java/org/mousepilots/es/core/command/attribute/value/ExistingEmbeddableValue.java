/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <E>
 */
public final class ExistingEmbeddableValue<E> extends Value<Proxy<E>,E,HasValue<E>,EmbeddableTypeES<E>> {

     private HasValue<E> encodedServerValue;
     
     private ExistingEmbeddableValue(){}

     private ExistingEmbeddableValue(EmbeddableTypeESImpl<E> type, Proxy<E> proxy) {
          super(type.getOrdinal(), proxy);
          encodedServerValue = type.wrap(proxy.__subject());
     }

     @Override
     protected HasValue<E> getEncodedServerValue() {
          return encodedServerValue;
     }
     
     ExistingEmbeddableValue(Proxy<E> proxy){
          this((EmbeddableTypeESImpl<E>) proxy.__getProxyAspect().getType(), proxy);
     }

     @Override @GwtIncompatible
     protected E decode(HasValue<E> encodedServerValue, ServerContext serverContext) {
          return encodedServerValue.getValue();
     }
     
     @Override
     public <R, A> R accept(ValueVisitor<R, A> valueVisitor, A arg) {
          return valueVisitor.visit(this, arg);
     }
     
}
