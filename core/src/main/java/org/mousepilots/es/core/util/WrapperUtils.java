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

     public static <E> HasValue wrapId(IdentifiableTypeES<? super E> type, E instance) {
          if (instance == null) {
               return null;
          } else {
               final SingularAttributeES idAttribute = getIdAttribute(type);
               final Object idValue = idAttribute.getJavaMember().get(instance);
               final TypeESImpl idType = (TypeESImpl) idAttribute.getType();
               return idType.wrap(idValue);
          }
     }    
    /**
     * Wraps a collection of {@code values} into the {@code wrappers} collection
     *
     * @param <A>
     * @param <TC>
     * @param attribute the {@code values}' attribute
     * @param values list of values which need to be wrapped.
     * @param wrappers the collection to which the wrappers must be added
     * @return the wrappers
     */
    public static <A extends Serializable, TC extends Collection<HasValue>> 
    TC wrapForDto(AttributeES attribute, Collection<A> values, TC wrappers)
    {
        if (values != null) {
            for(A value : values){
                wrappers.add(attribute.wrapForChange(value));
            }
        }
        return wrappers;
    }

    public static <A extends Serializable, TC extends Collection<HasValue>> 
    TC wrapForChange(AttributeES attribute, Collection<A> values, TC wrappers) 
    {
        if (values != null) {
            for(A value : values){
                wrappers.add(attribute.wrapForChange(value));
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
    TC unWrap(Collection<HasValue> valueWrappers, TC values)
    {
        if (valueWrappers != null) {
            for (HasValue<A> hv : valueWrappers) {
                values.add(hv.getValue());
            }
        }
        return values;
    }
}
