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
import org.mousepilots.es.core.command.attribute.UpdatePluralAttribute;
import org.mousepilots.es.core.command.attribute.value.Value;
import org.mousepilots.es.core.command.attribute.value.ValueFactory;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeES;
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
public final class UpdateCollection<E,EL,A extends Collection<EL>, AD extends PluralAttributeES<? super E, A, EL>> implements UpdatePluralAttribute<E,EL,A,AD,List<EL>>{
    
    private CollectionOperation collectionOperation;
    
    private transient List<EL> values;
    
    private LinkedList<Value<EL, EL, ?, ?>> serializableValues;
    
    public UpdateCollection(TypeES<EL> elementType, CollectionOperation operation, Collection<EL> collection, Collection<EL> values){
        if(operation==CollectionOperation.ADD){
            Embeddables.assertIfEmbeddableThenCreated(elementType, (Collection) values);
        }
        this.values = operation.getNet(collection, values);
        this.serializableValues = ValueFactory.create(elementType, values, LinkedList::new);
    }

    public CollectionOperation getCollectionOperation() {
        return collectionOperation;
    }
    
    @Override
    public void executeOnClient(Update<E, ?, A, AD, ?> update){
        collectionOperation.execute(getAttributeValueOnClient(update), values);
    }

    @Override
    public void undo(Update<E, ?, A, AD, ?> update) {
        collectionOperation.inverse().execute(getAttributeValueOnClient(update), values);
    }

    @Override @GwtIncompatible
    public List<EL> getModificationOnServer(ServerContext serverContext) {
        if(values==null){
            final List<EL> modifiableValues = new ArrayList<>(serializableValues.size());
            Value.collectServerValues(serverContext, serializableValues, modifiableValues);
            this.values = Collections.unmodifiableList(modifiableValues);
        }
        return values;
    }

    @Override @GwtIncompatible
    public void executeOnServer(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
        collectionOperation.execute(getAttributeValueOnServer(update), getModificationOnServer(serverContext));
    }
    
    @Override
    public <O, I> O accept(UpdateAttributeVisitor<O, I> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
}