/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.change.impl.container.Container;
import org.mousepilots.es.core.change.impl.container.EmbeddableLocator;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author geenenju
 */
public class EmbeddableReference<E> extends SerializableReference<E, EmbeddableTypeESImpl<E>> {

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

     @Override
     public E get(EntityManager entityManager, Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToServerId){
          final Object managedContainer = this.container.find(entityManager, typeToClientIdToServerId);
          return EmbeddableLocator.locate(container, managedContainer, embeddable.getValue());
     }

}
