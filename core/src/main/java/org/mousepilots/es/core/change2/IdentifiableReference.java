/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 */
public final class IdentifiableReference<T,ID> extends SerializableReference<T,IdentifiableTypeESImpl<T>>{

     private HasValue<ID> id;
     
     private IdentifiableReference() {
     }

     public IdentifiableReference(Proxy<T> proxy) {
          super(proxy.__getProxyAspect().getType().getOrdinal());
          this.id = WrapperUtils.wrapId(getType(), proxy.__subject());
     }
     
     public ID getId() {
          return id==null ? null : id.getValue();
     }

     @Override
     public final T get(EntityManager entityManager, Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToServerId) {
          final ID clientId = getId();
          final IdentifiableTypeESImpl<T> type = getType();
          Maps.getOrDefault(typeToClientIdToServerId, clientId, type,clientId);
          return entityManager.find(type.getJavaType(), clientId);
     }
}
