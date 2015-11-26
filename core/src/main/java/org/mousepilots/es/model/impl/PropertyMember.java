package org.mousepilots.es.model.impl;

import java.util.Objects;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public class PropertyMember implements MemberES {

    private final Class<?> declaringClass;
    private final String name;
    private final Getter getter;
    private final Setter setter;
    private final int modifiers;

    /**
     * Create a new instance of this class.
     * @param declaringClass the class that is declaring this member.
     * @param name the name of this member.
     * @param getter the getter for this member.
     * @param setter the setter for this member.
     * @param modifiers the modifiers for this member.
     */
    public PropertyMember(Class<?> declaringClass, String name, Getter getter,
            Setter setter, int modifiers) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.modifiers = modifiers;
    }

    @Override
    public <T> T get(Object instance) {
        return (T) getter.invoke(instance);
    }

    @Override
    public void set(Object instance, Object value) {
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
        //Might have to get this from the constructor, if synthetic members exist.
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