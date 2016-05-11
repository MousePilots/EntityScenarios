/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.command.UpdateEmbeddable;
import org.mousepilots.es.core.command.UpdateEntity;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.model.proxy.collection.Observer;

/**
 *
 * @author jgeenen
 * @param <E>
 * @param <C>
 * @param <EL>
 * @param <AD>
 */
public abstract class PluralAttributeObserver<E,EL,C,AD extends PluralAttributeES<? super E,C,EL>> implements Observer<C>{

    private int attributeOrdinal;
    
    private Proxy<E> proxy;
    
    private TypeVisitor<Update<E, ?, C, AD,?>,UpdateAttribute<E,C,AD,?>> UPDATE_CREATOR = new TypeVisitor<Update<E, ?, C, AD, ?>, UpdateAttribute<E, C, AD, ?>>() {
        @Override
        public Update<E, ?, C, AD, ?> visit(BasicTypeES t, UpdateAttribute<E, C, AD, ?> arg) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public Update<E, ?, C, AD, ?> visit(EmbeddableTypeES t, UpdateAttribute<E, C, AD, ?> arg) {
            return new UpdateEmbeddable<>(proxy,getAttribute(),arg);
        }

        @Override
        public Update<E, ?, C, AD, ?> visit(MappedSuperclassTypeES t, UpdateAttribute<E, C, AD, ?> arg) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public Update<E, ?, C, AD, ?> visit(EntityTypeES t, UpdateAttribute<E, C, AD, ?> arg) {
            return new UpdateEntity<>(proxy,getAttribute(),arg);
        }
    };

    protected final void createAndExecute(UpdateAttribute<E, C, AD, ?> updateAttribute){
        final ProxyAspect<E> proxyAspect = proxy.__getProxyAspect();
        final ManagedTypeESImpl<E> type = proxyAspect.getType();
        final Update<E, ?, C, AD, ?> update = type.accept(UPDATE_CREATOR, updateAttribute);
        proxyAspect.doUnmanaged(() -> update.executeOnClient());
    }

    protected PluralAttributeObserver(){}    
    
    public PluralAttributeObserver(Proxy<E> proxy, AD attributeDescriptor) {
        this.attributeOrdinal = attributeDescriptor.getOrdinal();
        this.proxy = proxy;
    }
    
    protected final AD getAttribute(){
        return (AD) AbstractMetamodelES.getInstance().getAttribute(attributeOrdinal);
    }

    protected final Proxy<E> getProxy() {
        return proxy;
    }
    
    

    
}
