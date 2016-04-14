/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.maven.model.generator.model.attribute;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.mousepilots.es.core.util.Function;
import org.mousepilots.es.core.util.StringUtils;

/**
 * Determines the generic return and exception-types of a method as fully qualified strings. Generic methods may be polymorphic: their return types
 * or arguments 
 * @author geenenju
 */
public class GenericMethodInfo{
    
    private final Method method;
    
    private final Map<TypeVariable<?>, Type> typeVariable2Value;
    
    private final String returnType;
    
    private final List<String> parameterTypes, exceptionTypes;
    
    /**
     * Resolves the generic {@code type}
     * @param typeVariable2Value
     * @param type
     * @return 
     */
    private String getGenericTypeString(Type type)
    {
        if(type instanceof Class) {
            //(raw) class
            return ((Class)type).getCanonicalName();
        } else if(type instanceof ParameterizedType){
            // e.g. Map<K,V>
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return getGenericTypeString(parameterizedType.getRawType()) + "<" 
                + StringUtils.join(
                    Arrays.asList(parameterizedType.getActualTypeArguments()), 
                    (Type actualTypeArgument) -> getGenericTypeString(actualTypeArgument), 
                    ","
                ) + 
            ">";

        } else if(type instanceof TypeVariable){
            //e.g. T
            final TypeVariable<?> typeVariable = (TypeVariable) type;
            final Type typeVariableValue = typeVariable2Value.get(typeVariable);
            if(typeVariableValue==null){
                return typeVariable.getName();
            } else {
                return getGenericTypeString(typeVariableValue);
            }
         } else if(type instanceof GenericArrayType){
             //e.g. T[]
            final GenericArrayType genericArrayType = (GenericArrayType) type;
            return getGenericTypeString(genericArrayType.getGenericComponentType()) + "[]";
         }
        else {
            throw new IllegalArgumentException("unsupported type " + type);
        }
    }
    

    /**
     * 
     * @param hasMethod the class having the {@code method}. The method may be inherited or declared.
     * @param method the {@code method} on {@code hasMethod}
     */
    public GenericMethodInfo(Class hasMethod, Method method) {
        this.method = method;
        this.typeVariable2Value = TypeUtils.getTypeArguments(hasMethod, Object.class);
        this.returnType = getGenericTypeString(this.method.getGenericReturnType());
        this.exceptionTypes = Function.apply(
            this.method.getGenericExceptionTypes(), 
            (Type et) -> getGenericTypeString(et) 
        );
        this.parameterTypes = Function.apply(
            this.method.getGenericParameterTypes(), 
            (Type et) -> getGenericTypeString(et) 
        );

    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }
    
    public String getReturnType(){
        return returnType;
    }

    public List<String> getExceptionTypes(){
        return exceptionTypes;
    }
}
