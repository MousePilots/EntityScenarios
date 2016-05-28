/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.container;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.util.Framework;

/**
 * Sets  {@link ProxyAspect#container} on {@link Proxy}-instances of existing embeddables. This enables
 * their remote update.
 * @author AP34WV
 */
@Framework
public class Containerizer {

    private final Set<Proxy> visited = new HashSet();

    private final AttributeVisitor<Void, Proxy> attributeValueContainerSetter = new AttributeVisitor<Void, Proxy>() {

        private Container createContainer(Proxy parent, AttributeES a) {
            if(parent.__getProxyAspect().getType() instanceof EmbeddableTypeES){
                return new EmbeddableContainer(parent, a);
            } else {
                return new IdentifiableContainer(parent, a);
            }
        }

        private Void visitJavaUtilCollection(PluralAttributeES a, Proxy parent) {
            final Collection collection = (Collection) a.getJavaMember().get(parent);
            final TypeES elementType = a.getElementType();
            if (collection != null && !collection.isEmpty() && elementType instanceof ManagedTypeES) {
                final Collection<Proxy> children = collection;
                if (elementType instanceof EmbeddableTypeES) {
                    final Container container = createContainer(parent, a);
                    for (Proxy child : children) {
                        child.__getProxyAspect().setContainer(container);
                    }
                }
                containerize(children);
            }
            return null;

        }

        @Override
        public Void visit(SingularAttributeES a, Proxy parent) {
            final Object value = a.getJavaMember().get(parent);
            final TypeES type = a.getType();
            if (value != null && type instanceof ManagedTypeES) {
                final Proxy child = (Proxy) value;
                if(type instanceof EmbeddableTypeES){
                    final Container container = createContainer(parent, a);
                    child.__getProxyAspect().setContainer(container);
                }
                containerize(child);

            }
            return null;
        }

        @Override
        public Void visit(CollectionAttributeES a, Proxy parent) {
            return visitJavaUtilCollection(a, parent);
        }

        @Override
        public Void visit(ListAttributeES a, Proxy parent) {
            return visitJavaUtilCollection(a, parent);
        }

        @Override
        public Void visit(SetAttributeES a, Proxy parent) {
            return visitJavaUtilCollection(a, parent);
        }

        @Override
        public Void visit(MapAttributeES a, Proxy parent) {
            final Map map = (Map) a.getJavaMember().get(parent);
            if (map!=null && !map.isEmpty()) {
                
                //handle keys
                final TypeES keyType = a.getKeyType();
                if (keyType instanceof ManagedTypeES) {
                    final Set<Proxy> keys = (Set<Proxy>) map.keySet();
                    if(keyType instanceof EmbeddableTypeES){
                        final Container keyContainer = createContainer(parent, a);
                        for (Proxy key : keys) {
                            key.__getProxyAspect().setContainer(keyContainer);
                        }
                    }
                    containerize(keys);
                }
                
                //handle values
                final TypeES valueType = a.getElementType();
                if (valueType instanceof ManagedTypeES) {
                    if(valueType instanceof EmbeddableTypeES){
                        for (Map.Entry<Object, Proxy> entry : (Set<Map.Entry>) map.entrySet()) {
                            final Container valueContainer = createContainer(parent, a);
                            valueContainer.setMapKey(entry.getKey());
                            entry.getValue().__getProxyAspect().setContainer(valueContainer);
                        }
                    }
                    containerize(map.values());
                }
            }
            return null;
        }
    };

    public <T> void containerize(Proxy<T> proxy) {
        if (!visited.contains(proxy)) {
            visited.add(proxy);
            final ManagedTypeESImpl<T> proxyType = proxy.__getProxyAspect().getType();
            final SortedSet<AttributeES<? super T, ?>> attributes = (SortedSet) proxyType.getAttributes();
            for (AttributeES attribute : attributes) {
                attribute.accept(attributeValueContainerSetter, proxy);
            }
        }
    }

    public void containerize(Collection<Proxy> proxies) {
        for (Proxy proxy : proxies) {
            containerize(proxy);
        }
    }
}
