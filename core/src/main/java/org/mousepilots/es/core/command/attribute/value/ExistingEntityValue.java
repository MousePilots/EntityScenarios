/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 */
public final class ExistingEntityValue<E,ID> extends Value<Proxy<E>,E,HasValue<ID>,EntityTypeES<E>>{

     private HasValue<ID> wrappedId;
     
     private ExistingEntityValue(){}

     private ExistingEntityValue(EntityTypeESImpl<E> type, Proxy<E> entity) {
          super(type.getOrdinal(), entity);
          wrappedId = WrapperUtils.getWrappedId(type, entity.__subject());
     }
     
     ExistingEntityValue(Proxy<E> entity){
          this((EntityTypeESImpl<E>)entity.__getProxyAspect().getType(),entity);
     }

     @Override
     protected HasValue<ID> getEncodedServerValue() {
          return wrappedId;
     }
     
     @Override
     protected E decode(HasValue<ID> encodedServerValue, ServerContext serverContext){
          if(encodedServerValue==null){
               return null;
          } else {
               final EntityTypeES<E> type = getType();
               final ID id = encodedServerValue.getValue();
               final Class<E> javaType = type.getJavaType();
               return serverContext.getEntityManager().find(javaType, id);
          }
     }
     
     @Override
     public <R, A> R accept(ValueVisitor<R, A> valueVisitor, A arg) {
          return valueVisitor.visit(this, arg);
     }
     
}
