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
     * @param forDTO if the attribute needs to wrap it for a DTO or a change.
     * @return the wrappers
     */
    public static <A extends Serializable, TC extends Collection<HasValue>> TC wrap(AttributeES attribute, Collection<A> values, TC wrappers, boolean forDTO) {
        if (values != null) {
            values.stream().forEach((A source) -> {
                if(forDTO){
                    wrappers.add(attribute.wrapForDTO(source));
                }else{
                    wrappers.add(attribute.wrapForChange(source));
                }
            });
        }
        return wrappers;
    }

//    /**
//     * Wraps the {@code identifiables}' Ids.
//     *
//     * @param <TI>
//     * @param <TC>
//     * @param identifiables
//     * @param wrappedIds
//     * @return
//     */
//    public static <TI, TC extends Collection<TI>> TC wrapIds(Collection<DTO> identifiables, TC wrappedIds) {
//        if (identifiables != null) {
//            for (DTO dto : identifiables) {
//                final AttributeES idAttribute = dto.getType().getId();
//                wrappedIds.add((TI) idAttribute.wrap(dto.getIdValue()));
//            }
//        }
//        return wrappedIds;
//    }

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
