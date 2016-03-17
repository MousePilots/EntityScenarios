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
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 *
 * @author geenenju
 */
public final class UpdateBasicCollection<E, EL, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A,EL>> extends UpdateCollection<E,EL,BasicTypeESImpl<EL>,A,AD,HasValue<EL>> {
     
     private UpdateBasicCollection(){}

     public UpdateBasicCollection(CollectionOperation elementOperation, Proxy<E> updated, SingularAttributeES<? super E,A> attribute, A values) {
          super(elementOperation,values);
     }

     @Override
     protected ArrayList<HasValue<EL>> encodeServerValues(BasicTypeESImpl<EL> elementType) {
          return HasValue.wrapElements(elementType, getClientValues(), new ArrayList<>(getNetClientValues().size()));
     }
     
     @Override
     protected List<EL> getRealNetValues(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
          final List<HasValue<EL>> encodedServerValues = getEncodedServerValues();
          return WrapperUtils.unWrap(encodedServerValues, new ArrayList<>(encodedServerValues.size()));
     }
     
}
