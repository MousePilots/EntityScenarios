/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.maven.model.generator.plugin;

import java.lang.reflect.Method;

/**
 * Used to specify a non standard java beans property's getter and/or setter methods.
 * @author geenenju
 */
public final class PropertyDefinition {
    
    public boolean appliesTo(Class javaType){
        if(javaType.getCanonicalName().equals(declaringClass.trim())){
            return true;
        } else {
            Class superJavaType = javaType.getSuperclass();
            return superJavaType==Object.class ? false : appliesTo(superJavaType);
        }
    }
    /**
     * The fully qualified name of the class declaring the method
     */
    private String declaringClass;
    
    /**
     * The property name, which must correspond to some JPA generated meta-model field name
     */
    private String name;
    
    /**
     * The fully qualified name of property's java type
     */
    private String javaType;
    
    /**
     * The short name of the getter e.g. {@code "getCount"}
     */
    private String getter;
    
    /**
     * The short name of the getter e.g. {@code "setCount"}
     */
    private String setter;
    
    /**
     * 
     * @return 
     */
    public Class getDeclaringClass() {
        try {
            return Class.forName(declaringClass);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("cannot load declaring class for " + this);
        }
    }

    public String getName() {
        return name;
    }

    public Method getGetter(){
        try {
            final Method getterMethod = getDeclaringClass().getMethod(getter);
            if(getJavaType().isAssignableFrom(getterMethod.getReturnType())){
                return getterMethod;
            } else {
                throw new IllegalStateException(
                    "return type of " + getterMethod + " is no subtype of the javaType specified in " + this
                );
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException("cannot locate getter for " + this,ex);
        }
    }

    
    

    public Method getSetter() {
        try {
            return getDeclaringClass().getMethod(setter,getJavaType());
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException("cannot locate setter for " + this,ex);
        }

    }

    public Class getJavaType() {
        try {
            return Class.forName(javaType);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("cannot load javaType for " + this,ex);
        }

    }

    @Override
    public String toString() {
        return "PropertyDefinition{" + "declaringClass=" + declaringClass + ", name=" + name + ", javaType=" + javaType + ", getter=" + getter + ", setter=" + setter + '}';
    }
    
    
}
