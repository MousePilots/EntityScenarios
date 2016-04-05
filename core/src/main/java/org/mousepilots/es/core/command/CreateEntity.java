/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import java.util.Objects;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <ID>
 */
public final class CreateEntity<E, ID> extends Create<E, EntityTypeES<E>> implements IdentifiableTypeCommand<E, ID, EntityTypeES<E>> {

    private HasValue<ID> id;

    private CreateEntity() {
        super();
    }

    private void assignId(final E entity) {
        final SingularAttributeES<? super E, ID> idAttribute = getIdAttribute();
        final MemberES<? super E, ID> javaMember = idAttribute.getJavaMember();
        javaMember.set(entity, getId());
    }

    public CreateEntity(EntityManagerImpl entityManager, EntityTypeES<E> typeDescriptor, ID id) {
        super(typeDescriptor, entityManager);
        final SingularAttributeES<? super E, ID> idAttribute = getIdAttribute();
        if (idAttribute.isGenerated()) {
            id = idAttribute.getGenerator().generate();
        } else {
            Objects.requireNonNull(id, "id required for " + typeDescriptor);
        }
        this.id = WrapperUtils.wrapValue(idAttribute, id);
    }

    @Override
    public ID getId() {
        return HasValue.getValueNullSafe(id);
    }

    public ID getServerId() {
        final E realSubject = getRealSubject();
        Objects.requireNonNull(realSubject, "real subject not available yet: first invoke void executeOnServer(ServerContext serverContext)");
        return getIdAttribute().getJavaMember().get(realSubject);
    }

    @Override
    protected final Proxy<E> createProxy() {
        final Proxy<E> proxy = getType().createProxy();
        assignId(proxy.__subject());
        return proxy;
    }

    @Override @GwtIncompatible
    public void executeOnServer(ServerContext serverContext) {
        super.executeOnServer(serverContext);
        final E entity = getRealSubject();
        final EntityManager entityManager = serverContext.getEntityManager();
        if (!getIdAttribute().isGenerated()) {
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
