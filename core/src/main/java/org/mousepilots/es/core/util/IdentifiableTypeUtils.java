package org.mousepilots.es.core.util;

import java.util.Collection;
import javax.persistence.Id;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Utilities class for identifiable types.
 *
 * @author Jurjen van Geenen
 * @version 1.0, 25-11-2015
 */
public class IdentifiableTypeUtils {
     
     public static <E> Object getId(IdentifiableTypeES<E> type, E instance) {
          if (instance == null) {
               return null;
          } else {
               final SingularAttributeES idAttribute = getIdAttribute(type);
               return idAttribute.getJavaMember().get(instance);
          }
     }
     

     
     public static <E> SingularAttributeES<? super E, ?> getIdAttribute(IdentifiableTypeES<E> type) {
          return type.getId(type.getIdType().getJavaType());
     }
     
     public static <E> Object getId(MetamodelES metaModel, E instance) {
          if (instance == null) {
               return null;
          } else {
               final Class<E> instanceClass = (Class<E>) instance.getClass();
               final IdentifiableTypeES<E> managedType = (IdentifiableTypeES) metaModel.managedType(instanceClass);
               return getId(managedType, instance);
          }
     }

     /**
      * Adds the {@link Id}-values of the {@code identifiables} to the supplied
      * {@code ids} collection.
      *
      * @param <E> the type of the identifiable type.
      * @param <I> the type of the id.
      * @param type the identifiable type to add the id for.
      * @param identifiables a collection of the type for the identifable type.
      * @param ids a collection to add the ids to.
      * @return the supplied {@link ids} method for chaining
      */
     public static <E, I> Collection<I> addIds(IdentifiableTypeESImpl<E> type, Collection<E> identifiables, Collection<I> ids) {
          final MemberES idMember = (MemberES) type.getId().getJavaMember();
          for (Object identifiable : identifiables) {
               ids.add(idMember.get(identifiable));
          }
          return ids;
     }
}
