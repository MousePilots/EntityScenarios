/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <EL>
 * @param <A>
 * @param <AD>
 */
public class RemoveFromEmbeddableCollection<E, EL, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A,EL>> extends UpdateCollection<E,EL,EmbeddableTypeESImpl<EL>,A,AD,HasValue<EL>> {

     private RemoveFromEmbeddableCollection(){}
     
     public RemoveFromEmbeddableCollection(A values) {
          super(CollectionOperation.REMOVE, values);
     }

     @Override
     protected ArrayList<HasValue<EL>> encodeServerValues(EmbeddableTypeESImpl<EL> elementType) {
          return HasValue.wrapElements(elementType, getClientValues(), new ArrayList<>(getNetClientValues().size()));
     }

     @Override
     protected List<EL> getRealNetValues(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
          final List<HasValue<EL>> netHasValues = getEncodedServerValues();
          return WrapperUtils.unWrap(netHasValues, new ArrayList<>(netHasValues.size()));
     }
     
}
