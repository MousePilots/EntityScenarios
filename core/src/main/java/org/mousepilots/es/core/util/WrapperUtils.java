package org.mousepilots.es.core.util;

import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import static org.mousepilots.es.core.util.IdentifiableTypeUtils.getIdAttribute;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public class WrapperUtils {

    /*
     * To make sure no instance can be initiated
     */
    private WrapperUtils() {
    }

     public static <E,ID> HasValue<ID> getWrappedId(IdentifiableTypeES<? super E> type, E instance) {
          if (instance == null) {
               return null;
          } else {
               final SingularAttributeES idAttribute = getIdAttribute(type);
               final Object idValue = idAttribute.getJavaMember().get(instance);
               return wrapValue(idAttribute, idValue);
          }
     }    
     
     public static <E,V> HasValue<V> getWrappedVersion(IdentifiableTypeES<? super E> type, E instance) {
          if (instance == null || !type.hasVersionAttribute()) {
               return null;
          } else {
               final SingularAttributeES versionAttribute = type.getVersion(null);
               final Object version = versionAttribute.getJavaMember().get(instance);
               return wrapValue(versionAttribute, version);
          }
     }    
     

     public static <T> HasValue<T> wrapValue(final SingularAttributeES<?,T> attribute, final T value) {
          if(value==null){
               return null;
          } else {
               final TypeESImpl<T> valueType = (TypeESImpl) attribute.getType();
               return valueType.wrap(value);
          }
     }
     





}
