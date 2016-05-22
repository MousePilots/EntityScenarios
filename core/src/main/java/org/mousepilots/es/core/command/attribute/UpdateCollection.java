/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.Embeddables;
import org.mousepilots.es.core.command.UpdateAttributeVisitor;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.command.attribute.value.ValueFactory;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.proxy.collection.Observable;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <EL>
 * @param <A>
 * @param <AD>
 */
public final class UpdateCollection<E, EL, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A, EL>> extends UpdatePluralAttributeImpl<E, EL, A, AD, List<EL>> {

    private CollectionOperation collectionOperation;

    private transient List<EL> values;

    private LinkedList<Value<EL, EL, ?, ?>> serializableValues;

    private A getDelegateCollection(Update<E, ?, A, AD, ?> update) {
        final Observable<A, ?> observable = (Observable<A, ?>) getAttributeValueOnClient(update);
        return observable.getDelegate();
    }

    private UpdateCollection() {
    }

    public UpdateCollection(TypeES<EL> elementType, CollectionOperation operation, Collection<EL> collection, Collection<EL> values) {
        if (operation == CollectionOperation.ADD) {
            Embeddables.assertIfEmbeddableThenCreated(elementType, (Collection) values);
        }
        this.collectionOperation = operation;
        this.values = operation.getNet(collection, values);
        this.serializableValues = ValueFactory.create(elementType, values, LinkedList::new);
    }

    public CollectionOperation getCollectionOperation() {
        return collectionOperation;
    }

    @Override
    public void executeOnClient(Update<E, ?, A, AD, ?> update) {
        collectionOperation.execute(getDelegateCollection(update), values);
    }

    @Override
    public void undo(Update<E, ?, A, AD, ?> update) {
        collectionOperation.inverse().execute(getDelegateCollection(update), values);
    }

    @Override @GwtIncompatible
    protected List<EL> doGetModificationOnServer(ServerContext serverContext) {
        final List<EL> modifiableValues = new ArrayList<>(serializableValues.size());
        Value.collectServerValues(serverContext, serializableValues, modifiableValues);
        return Collections.unmodifiableList(modifiableValues);
    }

    @Override
    @GwtIncompatible
    public void executeOnServer(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
        collectionOperation.execute(getNonNullAttributeValueOnServer(update), getModificationOnServer(serverContext));
    }

    @Override
    public <O, I> O accept(UpdateAttributeVisitor<O, I> visitor, I arg) {
        return visitor.visit(this, arg);
    }

}
