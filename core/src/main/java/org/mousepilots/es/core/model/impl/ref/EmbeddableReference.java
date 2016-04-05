/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.ref;

import org.mousepilots.es.core.model.impl.container.Container;
import org.mousepilots.es.core.model.impl.container.EmbeddableLocator;
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
public final class EmbeddableReference<E> extends SerializableReference<E, EmbeddableTypeESImpl<E>> {

     private EmbeddableReference() {
     }

     private HasValue<E> embeddable;

     private Container container;

     public EmbeddableReference(Proxy<E> embeddable) {
          super(embeddable.__getProxyAspect().getType().getOrdinal());
          final EmbeddableTypeESImpl<E> type = getType();
          this.embeddable = type.wrap(type.copy(embeddable.__subject()));
          this.container = embeddable.__getProxyAspect().getContainer().copy();
     }

     @Override @GwtIncompatible
     public E resolve(ServerContext serverContext){
          final Object managedContainer = this.container.resolve(serverContext);
          return EmbeddableLocator.locate(container, managedContainer, embeddable.getValue());
     }

}
