/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.maven.model.generator.model.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.mousepilots.es.core.model.impl.hv.AbstractHasValue;

/**
 *
 * @author jgeenen
 */
public final class HasValueDescriptor<T> implements Comparable<HasValueDescriptor> {
    
    private static final String HASVALUE_SUBPACKAGE = "org.mousepilots.es.hv";
    
    public static String getHasValueClassName() {
        return AbstractHasValue.class.getCanonicalName();
    }

    public static final class Factory{
        

        private final Map<Class, HasValueDescriptor> valueClass2HasValueDescriptor = new HashMap<>();

        private final String basePackageName;

        public Factory(String basePackageName) {
            if (basePackageName != null && !basePackageName.isEmpty()) {
                this.basePackageName = basePackageName;
            } else {
                throw new IllegalArgumentException("empty basePackageName");
            }
        }

        public <T> HasValueDescriptor<T> getInstance(Class<T> valueClass) {
            if (!valueClass2HasValueDescriptor.containsKey(valueClass)) {
                valueClass2HasValueDescriptor.put(
                        valueClass,
                        new HasValueDescriptor<>(basePackageName, valueClass)
                );
            }
            return valueClass2HasValueDescriptor.get(valueClass);
        }

    }

    private final String packageName;
    private final String valueClassCanonicalName;
    private final String hasValueImplClassSimpleName;
    private final String hasValueImplClassCanonicalName;
    

    private HasValueDescriptor(String basePackage, Class<T> valueClass) {
        this.valueClassCanonicalName = valueClass.getCanonicalName();
        this.hasValueImplClassSimpleName = "Has" + valueClass.getSimpleName() + "_ES";
        final TypeDescriptor typeDescriptor = TypeDescriptor.getInstance(valueClass);
        if(typeDescriptor instanceof ManagedTypeDescriptor){
            this.packageName = ((ManagedTypeDescriptor) typeDescriptor).getPackageName();
        } else {
            this.packageName = String.join(".", basePackage, valueClass.getPackage().getName());
        }
        this.hasValueImplClassCanonicalName = String.join(".", this.packageName, this.hasValueImplClassSimpleName);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getValueClassCanonicalName() {
        return valueClassCanonicalName;
    }

    public String getHasValueImplClassSimpleName() {
        return hasValueImplClassSimpleName;
    }

    public String getHasValueImplClassCanonicalName() {
        return hasValueImplClassCanonicalName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.hasValueImplClassCanonicalName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HasValueDescriptor<?> other = (HasValueDescriptor<?>) obj;
        if (!Objects.equals(this.hasValueImplClassCanonicalName, other.hasValueImplClassCanonicalName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(HasValueDescriptor o) {
        return this.valueClassCanonicalName.compareTo(o.valueClassCanonicalName);
    }

}
