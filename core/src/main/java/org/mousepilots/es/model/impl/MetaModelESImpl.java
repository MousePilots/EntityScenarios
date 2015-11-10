package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MetaModelES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 */
public class MetaModelESImpl implements MetaModelES {

    @Override
    public <X> EntityType<X> entity(Class<X> cls) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <X> ManagedTypeES<X> managedType(Class<X> cls) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <X> EmbeddableType<X> embeddable(Class<X> cls) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<ManagedType<?>> getManagedTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<EntityType<?>> getEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<EmbeddableType<?>> getEmbeddables() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}