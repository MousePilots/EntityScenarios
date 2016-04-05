/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.container;

import javax.persistence.Id;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;

/**
 * A container for an identifiable
 *
 * @author geenenju
 * @param <T> the identifiable's type
 * @param <I> the {@link Id}-type of the
 */
public final class IdentifiableContainer<T, I> extends Container<T, IdentifiableTypeES<T>, AttributeES<? super T, ?>> {

     private HasValue<I> id;

     private void setId(final I id) {
          final SingularAttributeES<? super T, ?> idAttribute = IdentifiableTypeUtils.getIdAttribute(getType());
          final TypeESImpl<I> idType = (TypeESImpl<I>) idAttribute.getType();
          this.id = idType.wrap(id);
     }

     protected IdentifiableContainer() {
          super();
     }


     public IdentifiableContainer(IdentifiableTypeES<T> type, AttributeES<? super T, ?> attribute, T entity) {
          super(type, attribute);
          final SingularAttributeES<? super T, I> idAttribute = (SingularAttributeES) IdentifiableTypeUtils.getIdAttribute(type);
          final I idValue = idAttribute.getJavaMember().get(entity);
          setId(idValue);
     }


     private IdentifiableContainer(IdentifiableTypeES<T> type, AttributeES<? super T, ?> attribute, I id, Object mapKey) {
          super(type, attribute);
          setId(id);
          setMapKey(mapKey);

     }

     public I getId() {
          return id.getValue();
     }

     @Override @GwtIncompatible
     public final T resolve(ServerContext serverContext) throws NullPointerException {
          final I clientId = getId();
          final IdentifiableTypeES<T> type = getType();
          final T identifiable = serverContext.find(type, clientId);
          if (identifiable == null) {
               throw new NullPointerException("no instance of " + type.getJavaType() + " found with client-id " + clientId);
          } else {
               return identifiable;
          }
     }

     @Override
     public Container copy() {
          return new IdentifiableContainer<>(getType(), getAttribute(), getId(), copyMapKey());
     }

}
