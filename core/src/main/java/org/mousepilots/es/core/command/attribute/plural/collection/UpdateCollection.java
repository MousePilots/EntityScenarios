/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 */
public abstract class UpdateCollection<E, EL, TDEL extends TypeESImpl<EL>, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A,EL>,SV> implements UpdateAttribute<E, A, AD>{

     private CollectionOperation collectionOperation;

     private transient A clientValues;
     
     private transient LinkedList<EL> netClientValues;
     
     private ArrayList<SV> encodedServerValues;

     
     protected abstract ArrayList<SV> encodeServerValues(TDEL elementTypeDescriptor);
     
     protected UpdateCollection(){}
     
     protected final A getClientCollection(Update<E, ?, A, AD, ?> update) {
          final AD attribute = update.getAttribute();
          final Proxy<E> proxy = update.getProxy();
          final A collection = attribute.getJavaMember().get(proxy.__subject());
          return collection;
     }
     
     protected UpdateCollection(CollectionOperation collectionOperation, A values){
          this.collectionOperation = collectionOperation;
          this.clientValues = values;
     }

     protected final TDEL getElementType(Update<E, ?, A, AD, ?> update){
          return (TDEL) update.getAttribute().getElementType();
     }

     protected final CollectionOperation getCollectionOperation() {
          return collectionOperation;
     }

     protected final A getClientValues() {
          return clientValues;
     }

     protected final List<EL> getNetClientValues() {
          return netClientValues;
     }

     protected final List<SV> getEncodedServerValues() {
          return encodedServerValues;
     }
     
     /**
      * For server-side use only. Gets the value of {@code update.getAttribute()} on {@link update.getRealSubject()} 
      * @param update
      * @return the value of {@code update.getAttribute()} on {@link update.getRealSubject()} 
      */
     protected final A decodeServerValues(Update<E, ?, A, AD, ?> update) {
          final E realSubject = update.getRealSubject();
          final AD attribute = update.getAttribute();
          final A collection = attribute.getJavaMember().get(realSubject);
          return collection;
     }
     
     protected abstract List<EL> getRealNetValues(Update<E, ?, A, AD, ?> update, ServerContext serverContext);
     
     @Override
     public void executeOnClient(Update<E, ?, A, AD, ?> update) {
          A collection = getClientCollection(update);
          this.netClientValues = getCollectionOperation().executeOnClient(collection, clientValues);
          this.encodedServerValues = encodeServerValues(getElementType(update));
     }     

     
     @Override
     public void undo(Update<E, ?, A, AD, ?> update) {
          A collection = getClientCollection(update);
          getCollectionOperation().inverse().executeOnClient(collection, netClientValues);
          netClientValues=null;
          encodedServerValues=null;
     }
     @Override
     public void redo(Update<E, ?, A, AD, ?> update) {
          executeOnClient(update);
     }

     @Override
     public final void executeOnServer(Update<E, ?, A, AD, ?> update, ServerContext serverContext){
          final List<EL> realNetValues = getRealNetValues(update,serverContext);
          final A realSubjectAttributeValue = decodeServerValues(update);
          getCollectionOperation().executeOnServer(realSubjectAttributeValue, realNetValues);
     }
     
     
}
