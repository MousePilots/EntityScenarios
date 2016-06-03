/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.maven.model.generator.plugin;

import java.lang.reflect.Method;
import javax.persistence.GeneratedValue;
import org.mousepilots.es.core.model.Generator;
import org.mousepilots.es.core.model.impl.DescendingLongGenerator;

/**
 * Used to specify java beans property's getter and/or setter methods and/or {@link Generator}.
 *
 * @author geenenju
 */
public final class PropertyDefinition {

    /**
     * The fully qualified name of the class declaring the method, required
     */
    private String declaringClass;

    /**
     * The property name, which must correspond to some JPA generated meta-model
     * field name, required
     */
    private String name;

    /**
     * The fully qualified name of property's java type required if {@link #getter} or {@link #setter} is specified
     */
    private String javaType;

    /**
     * To be specified only if non compliant to the Java conventions for getters/setters. 
     * Put the short name of the getter e.g. {@code "myNonCompliantGetCount"}.
     */
    private String getter;

    /**
     * To be specified only if non compliant to the Java conventions for getters/setters.
     * The short name of the getter e.g. {@code "myNonCompliantSetCount"}
     */
    private String setter;

    /**
     * The fully qualified name of a {@link Generator} class if applicable,
     * otherwise leave empty. A {@link Generator} may be specified for properties with a {@link GeneratedValue}.
     * If a property is generated and you do not specify the {@link #generatorClass}, the plugin defaults to {@link DescendingLongGenerator}
     */
    private String generatorClass;

    /**
     * @return the resolved {@link #declaringClass}
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

    public Method getGetter() {
        if(getter==null){
            return null;
        }
        try {
            final Method getterMethod = getDeclaringClass().getMethod(getter);
            if (getJavaType().isAssignableFrom(getterMethod.getReturnType())) {
                return getterMethod;
            } else {
                throw new IllegalStateException(
                        "return type of " + getterMethod + " is no subtype of the javaType specified in " + this
                );
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException("cannot locate getter for " + this, ex);
        }
    }

    public Method getSetter() {
        try {
            return getDeclaringClass().getMethod(setter, getJavaType());
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException("cannot locate setter for " + this, ex);
        }

    }

    /**
     * 
     * @return the resolved {@link #javaType}
     */
    public Class getJavaType() {
        try {
            return Class.forName(javaType);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("cannot load javaType for " + this, ex);
        }

    }

    /**
     * @return the resolved {@link #generatorClass} if any, otherwise {@code null}
     */
    public Class<? extends Generator> getGeneratorClass() {
        if (this.generatorClass == null || this.generatorClass.trim().isEmpty()) {
            return null;
        } else {
            try {
                return (Class<? extends Generator>) Class.forName(generatorClass);
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("cannot load generator for " + this, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "PropertyDefinition{" + "declaringClass=" + declaringClass + ", name=" + name + ", javaType=" + javaType + ", getter=" + getter + ", setter=" + setter + ", generatorClass=" + generatorClass + '}';
    }

    

}
