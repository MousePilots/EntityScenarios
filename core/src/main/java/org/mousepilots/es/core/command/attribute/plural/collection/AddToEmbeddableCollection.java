/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.command.CreateEmbeddable;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <EL>
 * @param <A>
 * @param <AD>
 */
public final class AddToEmbeddableCollection<E, EL, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A, EL>> extends UpdateCollection<E, EL, EmbeddableTypeESImpl<EL>, A, AD, CreateEmbeddable<EL>> {

     private AddToEmbeddableCollection(){}

     public AddToEmbeddableCollection(A values) {
          super(CollectionOperation.REMOVE, values);
          for (EL element : values) {
               final Proxy<EL> elementProxy = (Proxy<EL>) element;
               if (!elementProxy.__getProxyAspect().isCreated()) {
                    throw new UnsupportedOperationException(
                         "moving an embeddable value from managed instance's attribute to another is not supported"
                    );
               }
          }

     }

     @Override
     protected ArrayList<CreateEmbeddable<EL>> encodeServerValues(EmbeddableTypeESImpl<EL> elementTypeDescriptor) {
          final List<EL> netValues = super.getNetClientValues();
          ArrayList<CreateEmbeddable<EL>> serverValues = new ArrayList<>(netValues.size());
          for (EL netValue : netValues) {
               final Proxy<EL> netValueProxy = (Proxy<EL>) netValue;
               final ProxyAspect<EL> netValueProxyAspect = netValueProxy.__getProxyAspect();
               final CreateEmbeddable<EL> create = netValueProxyAspect.<CreateEmbeddable<EL>>getCreate();
               serverValues.add(create);
          }
          return serverValues;
     }

     @Override
     protected List<EL> getRealNetValues(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
          final List<CreateEmbeddable<EL>> encodedServerValues = getEncodedServerValues();
          final ArrayList<EL> realNetValues = new ArrayList<>(encodedServerValues.size());
          for (CreateEmbeddable<EL> create : encodedServerValues) {
               realNetValues.add(create.getRealSubject());
          }
          return realNetValues;
     }

}
