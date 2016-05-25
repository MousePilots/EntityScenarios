/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.container;

import java.util.LinkedList;
import java.util.List;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.impl.HasTypeAndAttribute;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 * A {@link Container} allows finding an embeddable.
 *
 * @author geenenju
 * @param <T>
 * @param <MT>
 * @param <A>
 */
public abstract class Container<T, MT extends ManagedTypeES<T>, A extends AttributeES> extends HasTypeAndAttribute<MT, A> {

    protected static final TypeVisitor<HasValue, Object> MAP_KEY_COPIER = new TypeVisitor<HasValue, Object>() {

        private HasValue visitIdentifiable(IdentifiableTypeESImpl t, Object key) {
            return key == null ? null : t.wrap(key);
        }

        @Override
        public HasValue visit(BasicTypeES t, Object key) {
            if (key == null) {
                return null;
            } else {
                final BasicTypeESImpl basicTypeESImpl = (BasicTypeESImpl) t;
                return basicTypeESImpl.wrap(key);
            }
        }

        @Override
        public HasValue visit(EmbeddableTypeES t, Object key) {
            if (key == null) {
                return null;
            } else {
                final EmbeddableTypeESImpl embeddableTypeES = (EmbeddableTypeESImpl) t;
                final Object copy = embeddableTypeES.shallowClone(key);
                return embeddableTypeES.wrap(copy);
            }
        }

        @Override
        public HasValue visit(MappedSuperclassTypeES t, Object key) {
            return visitIdentifiable((IdentifiableTypeESImpl) t, key);
        }

        @Override
        public HasValue visit(EntityTypeES t, Object key) {
            return visitIdentifiable((IdentifiableTypeESImpl) t, key);
        }
    };

    protected Container() {
        super();
    }

    private HasValue mapKey;

    protected Container(int typeOrdinal, int attributeOrdinal) {
        super(attributeOrdinal, typeOrdinal);
    }

    protected Container(MT type, AttributeES attribute) {
        super(attribute.getOrdinal(),type.getOrdinal());
    }

    Container getParent() {
        return null;
    }

    protected final Object getMapKey() {
        return mapKey == null ? null : mapKey.getValue();
    }

    protected final void setMapKey(Object mapKey) {
        MapAttributeES attribute = (MapAttributeES) getAttribute();
        final TypeESImpl keyType = (TypeESImpl) attribute.getKeyType();
        this.mapKey = keyType.wrap(mapKey);
    }

    protected final Object copyMapKey() throws IllegalArgumentException {
        final A attribute = getAttribute();
        final TypeES mapKeyType = attribute.getType();
        return (HasValue) mapKeyType.accept(Container.MAP_KEY_COPIER, getMapKey());
    }

    /**
     * Collects the path starting from some {@link IdentifiableContainer} up to
     * and including {@code this}. I.e. the first container in the list is an
     * {@link IdentifiableContainer}, followed by one or more
     * {@link EmbeddableContainer}, the last one of which is {code this.}
     *
     * @return the path starting from some {@link IdentifiableContainer} up to
     * and including {@code this}
     */
    protected final List<Container> collectPath() {
        final LinkedList<Container> retval = new LinkedList<>();
        for (Container container = this; container != null; container = container.getParent()) {
            retval.addFirst(container);
        }
        return retval;
    }

    /**
     * Resolves the managed instance corresponding to {@code this} on the server
     *
     * @param serverContext
     * @return the managed instance corresponding to {@code this}
     */
    @GwtIncompatible
    public abstract T resolve(ServerContext serverContext);

    public abstract Container copy();
}
