package org.mousepilots.es.core.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.mousepilots.es.core.command.attribute.CollectionObserverImpl;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.ObservableCollection;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Collection belongs to.
 * @param <E> The element type of the represented Collection.
 */
public class CollectionAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Collection<E>, E> implements CollectionAttributeES<T, E> {

    
    public CollectionAttributeESImpl(String name, int ordinal, int typeOrdinal, int declaringTypeOrdinal, Integer superOrdinal, Collection<Integer> subOrdinals, PersistentAttributeType persistentAttributeType, PropertyMember<T, Collection<E>> javaMember, AssociationES valueAssociation, int elementTypeOrdinal) {
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation, elementTypeOrdinal);
    }

    @Override
    public Collection<E> createEmpty() {
        return new ArrayList<>();
    }

    @Override
    protected Collection<E> createObserved(Proxy<T> proxy, Collection<E> value) {
        ObservableCollection<E> retval = new ObservableCollection<>(value);
        retval.addListener(new CollectionObserverImpl(proxy, this));
        return retval;
    }

    @Override
    public final <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    
    
    
    


}