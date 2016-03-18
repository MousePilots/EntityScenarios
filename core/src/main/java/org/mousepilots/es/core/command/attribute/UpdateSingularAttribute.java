/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.command.UpdateAttributeVisitor;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.command.attribute.value.ValueFactory;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 */
public final class UpdateSingularAttribute<E,A> implements UpdateAttribute<E, A, SingularAttributeES<? super E, A>, A>{
    
    private transient A oldValue, newValue;
    
    private Value<A, A, ?, ?> serializableValue;

    public UpdateSingularAttribute(SingularAttributeES<? super E, A> attribute, Proxy<E> proxy, A newValue) {
        this.oldValue = attribute.getJavaMember().get(proxy.__subject());
        this.newValue = newValue;
        this.serializableValue = ValueFactory.create(attribute.getType(), newValue);
    }
    
    @Override
    public void executeOnClient(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
        update.getAttribute().getJavaMember().set(update.getProxy().__subject(), newValue);
    }

    @Override
    public void undo(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update) {
        update.getAttribute().getJavaMember().set(update.getProxy().__subject(), oldValue);
    }

    @Override
    public A getModificationOnServer(ServerContext serverContext) {
        return serializableValue.getServerValue(serverContext);
    }
    
    @Override
    public void executeOnServer(Update<E, ?, A, SingularAttributeES<? super E, A>, ?> update, ServerContext serverContext) {
        final E realSubject = update.getRealSubject();
        final A serverValue = serializableValue==null ? null : serializableValue.getServerValue(serverContext);
        update.getAttribute().getJavaMember().set(realSubject, serverValue);
    }

    @Override
    public <O, I> O accept(UpdateAttributeVisitor<O, I> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
    
}
