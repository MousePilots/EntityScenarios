/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.IdentifiableTypeCommand;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.EntityManagerESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <ID>
 */
public final class CreateEntity<E,ID> extends Create<E,EntityTypeES<E>> implements IdentifiableTypeCommand<E,ID,EntityTypeES<E>> {

     private HasValue<ID> id;
     
     private CreateEntity() {
          super();
     }

     public CreateEntity(EntityManagerESImpl entityManager, EntityTypeES<E> typeDescriptor, ID id) {
          super(typeDescriptor, entityManager);
          final SingularAttributeES<? super E, ID> idAttribute = getIdAttribute();
          if(idAttribute.isGenerated()){
               id = idAttribute.getGenerator().generate();
          } else {
               if(id==null){
                    throw new IllegalArgumentException("id required for " + typeDescriptor);
               }
          }
          this.id = WrapperUtils.wrapValue(idAttribute, id);
     }

     private void assignId(final E entity) {
          final SingularAttributeES<? super E, ID> idAttribute = getIdAttribute();
          final MemberES<? super E, ID> javaMember = idAttribute.getJavaMember();
          javaMember.set(entity, getId());
     }

     @Override
     public ID getId() {
          return HasValue.getValueNullSafe(id);
     }
     
     @Override
     protected void doExecuteOnClient() {
          super.doExecuteOnClient();
          final Proxy<E> proxy = getProxy();
          final ProxyAspect proxyAspect = proxy.__getProxyAspect();
          proxyAspect.setManagedMode(false);
          final E proxySubject = proxy.__subject();
          assignId(proxySubject);
          proxyAspect.setManagedMode(true);
     }

     @Override
     public void executeOnServer(ServerContext serverContext) {
          super.executeOnServer(serverContext);
          final E entity = getRealSubject();
          final EntityManager entityManager = serverContext.getEntityManager();
          if(!getIdAttribute().isGenerated()){
               assignId(entity);
          }
          entityManager.persist(entity);
          serverContext.onExecuteOnServer(this);
     }
     @Override
     
     public <R, A> R accept(CommandVisitor<R, A> listener, A arg) {
          return listener.visit(this, arg);
     }     
     
     
}
