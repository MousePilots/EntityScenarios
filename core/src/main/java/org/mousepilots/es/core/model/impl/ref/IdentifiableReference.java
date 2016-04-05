/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.ref;

import java.util.Objects;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <ID>
 * @param <V>
 */
public final class IdentifiableReference<T,ID,V> extends SerializableReference<T,IdentifiableTypeESImpl<T>>{

     private HasValue<ID> id;
     
     private HasValue<V> version;
     
     private IdentifiableReference(){}

     public IdentifiableReference(Proxy<T> proxy) {
          super(proxy.__getProxyAspect().getType().getOrdinal());
          this.id = WrapperUtils.getWrappedId(getType(), proxy.__subject());
          final IdentifiableTypeESImpl<T> type = getType();
          if(type.hasVersionAttribute()){
               this.version = WrapperUtils.getWrappedVersion(type, proxy.__subject());
          }
     }
     
     public ID getId() {
          return HasValue.getValueNullSafe(id);
     }

     public V getVersion() {
          return HasValue.getValueNullSafe(version);
     }
     
     @Override @GwtIncompatible
     public final T resolve(ServerContext serverContext) {
          final ID clientId = getId();
          final IdentifiableTypeESImpl<T> type = getType();
          final ID serverId = serverContext.getServerId(type, clientId);
          final Class<T> javaType = type.getJavaType();
          final T retval = serverContext.getEntityManager().find(javaType, serverId);
          if(retval==null){
               throw new NullPointerException("instance of " + javaType + " with id " + serverId + " not found");
          }
          final V expectedVersion = getVersion();
          final Object encounteredVersion = IdentifiableTypeUtils.getVersion(type, retval);
          if(expectedVersion!=null && !Objects.equals(expectedVersion, encounteredVersion)){
               throw new IllegalStateException(
                    "version mismatch of " + javaType + " with id " + serverId + ". " +
                    "expected: " + expectedVersion + ", encountered: " + encounteredVersion);
          }
          return retval;
     }
}
