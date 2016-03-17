package org.mousepilots.es.core.util;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.core.model.AttributeES;
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
     
    /**
     * Wraps a collection of {@code values} into the {@code wrappers} collection
     *
     * @param <A>
     * @param <TC>
     * @param attribute the {@code values}' wrapAttribute
     * @param values list of values which need to be wrapped.
     * @param wrappers the collection to which the wrappers must be added
     * @return the wrappers
     */
    public static <A extends Serializable, TC extends Collection<HasValue>> 
    TC wrapForDto(AttributeES attribute, Collection<A> values, TC wrappers)
    {
        if (values != null) {
            for(A value : values){
                wrappers.add(attribute.wrap(value));
            }
        }
        return wrappers;
    }

    public static <A extends Serializable, TC extends Collection<HasValue>> 
    TC wrapForChange(AttributeES attribute, Collection<A> values, TC wrappers) 
    {
        if (values != null) {
            for(A value : values){
                wrappers.add(attribute.wrap(value));
            }
        }
        return wrappers;
    }

    /**
     * Unwraps a collection of {@code valueWrappers}
     *
     * @param <A>
     * @param <TC>
     * @param valueWrappers
     * @param values
     * @return
     */
    public static <A, TC extends Collection<A>> 
    TC unWrap(Collection<HasValue<A>> valueWrappers, TC values)
    {
        if (valueWrappers != null) {
            for (HasValue<A> hv : valueWrappers) {
                values.add(hv.getValue());
            }
        }
        return values;
    }
}
