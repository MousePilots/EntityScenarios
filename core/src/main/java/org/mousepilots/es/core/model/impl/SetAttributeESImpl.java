package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.mousepilots.es.core.command.attribute.SetObserverImpl;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.ObservableSet;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E> extends PluralAttributeESImpl<T, Set<E>, E> implements SetAttributeES<T, E> {

    @Override
    public Set<E> createEmpty() {
        return new HashSet<>();
    }

    @Override
    protected Set<E> createObserved(Proxy<T> proxy, Set<E> value) {
        ObservableSet<E> retval = new ObservableSet<>(value);
        retval.addListener(new SetObserverImpl<>(proxy,this));
        return retval;
    }

    public SetAttributeESImpl(
            String name, 
            int ordinal, 
            int typeOrdinal, 
            int declaringTypeOrdinal, 
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            PersistentAttributeType persistentAttributeType, 
            PropertyMember<T, Set<E>> javaMember, 
            AssociationES valueAssociation, 
            int elementTypeOrdinal) {
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation, elementTypeOrdinal);
    }

    public final <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    

}