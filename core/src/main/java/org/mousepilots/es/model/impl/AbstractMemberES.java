package org.mousepilots.es.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public abstract class AbstractMemberES implements MemberES {

    private Map<Object,Object> instanceValues = new HashMap<>();
    private String name;
    
    @Override
    public <T> T get(Object instance) {
        return (instanceValues.containsKey(instance)) ? (T) instanceValues.get(instance) : null;
    }

    @Override
    public void set(Object instance, Object value) {
        instanceValues.put(instance, value);
    }

    @Override
    public Class<?> getDeclaringClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getModifiers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSynthetic() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.instanceValues);
        hash = 47 * hash + Objects.hashCode(this.name);
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
        final AbstractMemberES other = (AbstractMemberES) obj;
        if (!Objects.equals(this.instanceValues, other.instanceValues)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}