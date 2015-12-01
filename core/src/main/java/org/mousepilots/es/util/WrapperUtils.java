package org.mousepilots.es.util;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;

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

    /**
     * Wraps a collection of {@code values} into the {@code wrappers} collection
     *
     * @param <A>
     * @param <TC>
     * @param attribute the {@code values}' attribute
     * @param values list of values which need to be wrapped.
     * @param wrappers the collection to which the wrappers must be added
     * @param dtoType
     * @return the wrappers
     */
    public static <A extends Serializable, TC extends Collection<HasValue>> TC wrapForDto(AttributeES attribute, Collection<A> values, TC wrappers, DtoType dtoType) {
        if (values != null) {
            for(A source : values){
                wrappers.add(attribute.wrapForDTO(source));
            }
        }
        return wrappers;
    }

    public static <A extends Serializable, TC extends Collection<HasValue>> TC wrapForChange(AttributeES attribute, Collection<A> values, TC wrappers, DtoType dtoType) {
        if (values != null) {
            for(A source : values){
                wrappers.add(attribute.wrapForChange(source));
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
    public static <A, TC extends Collection<A>> TC unWrap(Collection<HasValue> valueWrappers, TC values) {
        if (valueWrappers != null) {
            for (HasValue<A> source : valueWrappers) {
                values.add(source.getValue());
            }
        }
        return values;
    }
}
