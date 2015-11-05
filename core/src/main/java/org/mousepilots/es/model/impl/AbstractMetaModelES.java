package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.MetaModelES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractMetaModelES implements MetaModelES {

    @Override
    public <X> EntityType<X> entity(Class<X> cls) {
        return (cls != null) ? new AbstractEntityTypeES<X>(cls.getName(), cls.getSimpleName(), 6, Type.PersistenceType.ENTITY, cls) {
        } : null;
    }

    @Override
    public <X> ManagedType<X> managedType(Class<X> cls) {
        return (cls != null) ? new AbstractManagedTypeES<X>(cls.getName(), cls.getSimpleName(), 6, Type.PersistenceType.ENTITY, cls) {
        } : null;
    }

    @Override
    public <X> EmbeddableType<X> embeddable(Class<X> cls) {
        return (cls != null) ? new AbstractEmbeddableTypeES(cls.getName(), cls.getSimpleName(), 6, Type.PersistenceType.EMBEDDABLE, cls) {
        } : null;
    }

    @Override
    public Set<ManagedType<?>> getManagedTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<EntityType<?>> getEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<EmbeddableType<?>> getEmbeddables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
