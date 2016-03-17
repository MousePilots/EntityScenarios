/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.util.WrapperUtils;

/**
 * @author geenenju
 * @param <E>
 * @param <EL>
 * @param <A>
 * @param <AD>
 * @param <ID>
 */
public final class UpdateEntityCollection<E, EL, A extends Collection<EL>, AD extends PluralAttributeES<? super E, A,EL>,ID> extends UpdateCollection<E,EL,EntityTypeESImpl<EL>,A,AD,HasValue<ID>>  {

     private UpdateEntityCollection() {
     }

     public UpdateEntityCollection(CollectionOperation collectionOperation, A values) {
          super(collectionOperation,values);
     }

     
     
     @Override
     protected ArrayList<HasValue<ID>> encodeServerValues(EntityTypeESImpl<EL> elementTypeDescriptor) {
          final SingularAttributeES<? super EL, ID> idAttribute = (SingularAttributeES) IdentifiableTypeUtils.getIdAttribute(elementTypeDescriptor);
          final List<EL> netValues = getNetClientValues();
          ArrayList<HasValue<ID>> retval = new ArrayList<>(netValues.size());
          for(EL element : netValues){
               retval.add(HasValue.wrapAttribute(idAttribute, element));
          }
          return retval;
     }

     @Override
     protected List<EL> getRealNetValues(Update<E, ?, A, AD, ?> update, ServerContext serverContext) {
          final List<HasValue<ID>> netHasValues = getEncodedServerValues();
          final int netValueSize = netHasValues.size();
          final List<ID> elementIds = WrapperUtils.unWrap(netHasValues, new ArrayList<>(netValueSize));
          final EntityManager entityManager = serverContext.getEntityManager();
          final EntityTypeESImpl<EL> elementType = getElementType(update);
          final Class<EL> elementJavaType = elementType.getJavaType();
          switch(netValueSize){
               case 0 : {
                    return Collections.EMPTY_LIST;
               }
               case 1 : {
                    final EL element = entityManager.find(elementJavaType, elementIds.get(0));
                    return Collections.singletonList(element);
               }        
               default: {
                    final SingularAttributeES<? super EL, ?> elementIdAttribute = elementType.getId();
                    final String queryString = String.format(
                         "SELECT el FROM %1s el WHERE el.%2s IN ?1",
                         elementType.getName(),
                         elementIdAttribute.getName());
                    final TypedQuery<EL> query = entityManager.createQuery(queryString, elementJavaType);
                    return query.setParameter(1, elementIds).getResultList();
               }
          }
     }
}
