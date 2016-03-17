package org.mousepilots.es.core.model.impl;

import java.util.Objects;
import org.mousepilots.es.core.model.MemberES;

/**
 * @author Jurjen van Geenen
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 * @param <X>
 * @param <Y>
 */
public class PropertyMember<X,Y> implements MemberES<X,Y> {

    private final Class<?> declaringClass;
    private final String name;
    private final Getter<X,Y> getter;
    private final Setter<X,Y> setter;
    private final int modifiers;

    /**
     * Create a new instance of this class.
     * @param declaringClass the class that is declaring this member.
     * @param name the name of this member.
     * @param getter the getter for this member.
     * @param setter the setter for this member.
     * @param modifiers the modifiers for this member.
     */
    public PropertyMember(Class<?> declaringClass, String name, Getter<X,Y> getter, Setter<X,Y> setter, int modifiers) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.modifiers = modifiers;
    }

    @Override
    public Y get(X instance) {
        return getter.invoke(instance);
    }

    @Override
    public void set(X instance, Y value) {
        setter.invoke(instance, value);
    }

    @Override
    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getModifiers() {
        return modifiers;
    }

    @Override
    public boolean isSynthetic() {
        //TODO Might have to get this from the constructor, if synthetic members exist.
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.declaringClass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyMember other = (PropertyMember) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.declaringClass, other.declaringClass);
    }
}