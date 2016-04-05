package org.mousepilots.es.core.model;

import java.io.Serializable;
import java.util.Collection;
import org.mousepilots.es.core.model.impl.Constructor;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Limits the GWT serializer generator for {@link AttributeES} wrapAttribute values {@code this}' concrete subclasses.
 *
 * @param <T> the wrapped value's type
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 */
public interface HasValue<T> extends Serializable {

    /**
     *
     * @param <E>
     * @param <CV>
     * @param <CHV>
     * @param elementType
     * @param values
     * @param target
     * @return
     */
    public static <E, CV extends Collection<E>, CHV extends Collection<HasValue<E>>> CHV wrapElements(TypeES<E> elementType, CV values, CHV target) {
        TypeESImpl<E> elementTypeESImpl = (TypeESImpl<E>) elementType;
        for (E element : values) {
            target.add(elementTypeESImpl.wrap(element));
        }
        return target;
    }

    public static <E, A, AD extends AttributeES<? super E, A>> HasValue<A> wrapAttribute(AD attribute, E instance) {
        final A value = attribute.getJavaMember().get(instance);
        return wrapAttributeValue(attribute, value);
    }

    public static <A, AD extends AttributeES<?, A>> HasValue<A> wrapAttributeValue(AD attribute, A value) {
        if (value == null) {
            return null;
        } else {
            final TypeESImpl<A> type = (TypeESImpl<A>) attribute.getType();
            return type.wrap(value);
        }
    }

    public static <T> T getValueNullSafe(HasValue<T> hv) {
        return hv == null ? null : hv.getValue();
    }

    /**
     * @return the value wrapped by {@code this}
     */
    T getValue();

    /**
     * Set the value wrapped by {@code this}
     *
     * @param value the new value.
     */
    void setValue(T value);

}
