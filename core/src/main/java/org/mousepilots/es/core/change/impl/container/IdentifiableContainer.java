/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change.impl.container;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Id;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.util.Maps;

/**
 * A container for an identifiable
 *
 * @author geenenju
 * @param <T> the identifiable's type
 * @param <I> the {@link Id}-type of the
 */
public class IdentifiableContainer<T, I> extends Container<T, IdentifiableTypeES<T>, AttributeES<? super T, ?>> {

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
          final SingularAttributeES<? super T, ?> idAttribute = IdentifiableTypeUtils.getIdAttribute(type);
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

     @Override
     public final T find(EntityManager em, Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToGeneratedId) throws NullPointerException {
          final I clientId = getId();
          final IdentifiableTypeES<T> type = getType();
          final I idToUse = typeToClientIdToGeneratedId == null ? clientId : Maps.getOrDefault(typeToClientIdToGeneratedId, clientId, type, clientId);
          final Class<T> javaType = type.getJavaType();
          final T identifiable = em.find(javaType, idToUse);
          if (identifiable == null) {
               throw new NullPointerException("instance of " + javaType + " with id " + idToUse + " not found");
          } else {
               return identifiable;
          }
     }

     @Override
     public Container copy() {
          return new IdentifiableContainer<>(getType(), getAttribute(), getId(), copyMapKey());
     }

}
