/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.proxy;

import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.command.Create;
import org.mousepilots.es.core.command.Delete;
import org.mousepilots.es.core.model.impl.container.Container;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.util.Framework;

/**
 *
 * @author jgeenen
 * @param <T> the java-type {@code this} of the managed instance {@code this} is a proxy-aspect for
 */
@Framework
public final class ProxyAspect<T>{

    private int typeOrdinal;

    private Container container;

    private transient EntityManagerImpl entityManager;

    private boolean managedMode = false;

    private Create<T, ? extends ManagedTypeES<T>> create;

    private Delete<T, ? extends ManagedTypeES<T>> delete;

    protected ProxyAspect() {
    }

    public ProxyAspect(int typeOrdinal) {
        this.typeOrdinal = typeOrdinal;
    }

    public int getTypeOrdinal() {
        return typeOrdinal;
    }

    public void setCreate(Create create) {
        if (this.create == null || create == null) {
            this.create = create;
        } else {
            throw new IllegalStateException("create allready set");
        }
    }

    public <C extends Create<T, ? extends ManagedTypeES<T>>> C getCreate() {
        return (C) create;
    }

    public void setDelete(Delete<T, ?> delete) {
        if (this.delete == null || delete == null) {
            this.delete = delete;
        } else {
            throw new IllegalStateException("delete allready set");
        }
    }

    public Delete<T, ? extends ManagedTypeES<T>> getDelete() {
        return delete;
    }

    public boolean isCreated() {
        return create != null;
    }

    public boolean isDeleted() {
        return delete != null;
    }

    public ManagedTypeESImpl<T> getType() {
        return (ManagedTypeESImpl) AbstractMetamodelES.getInstance().getType(typeOrdinal);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        if (getType().getPersistenceType() != Type.PersistenceType.EMBEDDABLE) {
            throw new UnsupportedOperationException(this + " is no embeddable");
        } else if (this.container == null) {
            this.container = container;
        } else {
            throw new IllegalStateException("container allready set");
        }
    }

    /**
     * Gets the {@code transient} {@link EntityManagerImpl} {@code this} is managed by
     *
     * @return
     */
    public EntityManagerImpl getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManagerImpl entityManager) {
        if (this.entityManager == null) {
            this.entityManager = entityManager;
        } else {
            throw new IllegalStateException("this' managed type instance is allready managed by " + this.entityManager);
        }
    }

    public boolean isManagedMode() {
        return entityManager != null && managedMode;
    }

    public void setManagedMode(boolean managedMode) {
        this.managedMode = managedMode;
    }

}
