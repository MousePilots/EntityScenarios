package org.mousepilots.es.model;

import java.lang.reflect.Member;

/**
 * MemberES is an interface that reflects identifying information about a single member (a field or a method) or a constructor.
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 * see Member
 */
public interface MemberES extends Member {
    
    /**
     * Gets the value for the specified instance.
     * @param <T> the expected class of the value
     * @param instance the instance to which the value belongs
     * @return the value for the specified instance, if no value was set returnvalue will be {@code null}
     */
    <T> T get(Object instance);
    
    /**
     * Sets the given value for the given instance
     * @param instance the instance to which the value belongs
     * @param value the value which needs to be set
     */
    void set(Object instance, Object value);
    //Setter (set, add, remove en put) interceptie op implementatie niveau.
}
