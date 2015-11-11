package org.mousepilots.es.model;

import java.util.Set;
import javax.persistence.metamodel.IdentifiableType;

/**
 * Instances of the type {@link IdentifiableTypeES} represent entity or
 * mapped superclass types.
 * @param <T> The represented entity or mapped superclass type.
 * @see ManagedTypeES
 * @see IdentifiableType
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface IdentifiableTypeES<T>  extends ManagedTypeES<T>, IdentifiableType<T>{

    <Y> SingularAttributeES<? super T, Y> getId(Class<Y> type);
    <Y> SingularAttributeES<T, Y> getDeclaredId(Class<Y> type);
    <Y> SingularAttributeES<? super T, Y> getVersion(Class<Y> type);
    <Y> SingularAttributeES<T, Y> getDeclaredVersion(Class<Y> type);
    IdentifiableTypeES<? super T> getSupertype();
}