package org.mousepilots.es.core.model;

import java.lang.reflect.Member;

/**
 * MemberES is an interface that reflects identifying information about a single member (a field or a method) or a constructor.
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 * see Member
 */
public interface MemberES<X,Y> extends Member {

    /**
     * Gets the value for the specified instance.
     * @param instance the instance to which the value belongs
     * @return the value for the specified instance, if no value was set returnvalue will be {@code null}
     */
    Y get(X instance);

    /**
     * Sets the given value for the given instance
     * @param instance the instance to which the value belongs
     * @param value the value which needs to be set
     */
    void set(X instance, Y value);
}