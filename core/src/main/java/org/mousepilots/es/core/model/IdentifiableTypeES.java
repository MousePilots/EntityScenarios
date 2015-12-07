package org.mousepilots.es.core.model;

import javax.persistence.metamodel.IdentifiableType;

/**
 * Instances of the type {@link IdentifiableTypeES} represent entity or
 * mapped superclass types.
 * @param <T> The represented entity or mapped superclass type.
 * @see ManagedTypeES
 * @see IdentifiableType
 * @author Roy Cleven
 * @version 1.0, 25-11-2015
 */
public interface IdentifiableTypeES<T>  extends ManagedTypeES<T>, IdentifiableType<T>{

    @Override
    <Y> SingularAttributeES<? super T, Y> getId(Class<Y> type);
    @Override
    <Y> SingularAttributeES<T, Y> getDeclaredId(Class<Y> type);
    @Override
    <Y> SingularAttributeES<? super T, Y> getVersion(Class<Y> type);
    @Override
    <Y> SingularAttributeES<T, Y> getDeclaredVersion(Class<Y> type);
    @Override
    IdentifiableTypeES<? super T> getSupertype();
}