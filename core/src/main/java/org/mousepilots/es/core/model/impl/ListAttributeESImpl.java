package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.command.attribute.ListObserverImpl;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.ObservableList;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E> extends PluralAttributeESImpl<T, List<E>, E> implements ListAttributeES<T, E> {

    public ListAttributeESImpl(
            String name, 
            int ordinal, 
            int typeOrdinal, 
            int declaringTypeOrdinal, 
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            PersistentAttributeType persistentAttributeType, 
            PropertyMember<T, List<E>> javaMember, 
            AssociationES valueAssociation, 
            int elementTypeOrdinal) {
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation, elementTypeOrdinal);
    }

    @Override
    protected List<E> createObserved(Proxy<T> proxy, List<E> value) {
        ObservableList<E> retval = new ObservableList<>(value);
        retval.addListener(new ListObserverImpl<>(proxy, this));
        return retval;
    }

    public final <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

}