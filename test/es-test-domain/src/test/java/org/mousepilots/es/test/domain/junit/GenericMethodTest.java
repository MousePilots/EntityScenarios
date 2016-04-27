/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.domain.junit;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeResolver;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.Test;
import org.mousepilots.es.test.domain.entities.Manager;
import org.mousepilots.es.test.domain.entities.User;

/**
 *
 * @author geenenju
 */
public class GenericMethodTest {

    private static final Logger LOG = Logger.getLogger(GenericMethodTest.class.getName());

    public static <T> Type[] resolveActualTypeArgs(Class<? extends T> offspring, Class<T> base, Type... actualArgs) {

        assert offspring != null;
        assert base != null;
        assert actualArgs.length == 0 || actualArgs.length == offspring.getTypeParameters().length;

        //  If actual types are omitted, the type parameters will be used instead.
        if (actualArgs.length == 0) {
            actualArgs = offspring.getTypeParameters();
        }
        // map type parameters into the actual types
        Map<String, Type> typeVariables = new HashMap<String, Type>();
        for (int i = 0; i < actualArgs.length; i++) {
            TypeVariable<?> typeVariable = (TypeVariable<?>) offspring.getTypeParameters()[i];
            typeVariables.put(typeVariable.getName(), actualArgs[i]);
        }

        // Find direct ancestors (superclass, interfaces)
        List<Type> ancestors = new LinkedList<Type>();
        if (offspring.getGenericSuperclass() != null) {
            ancestors.add(offspring.getGenericSuperclass());
        }
        for (Type t : offspring.getGenericInterfaces()) {
            ancestors.add(t);
        }

        // Recurse into ancestors (superclass, interfaces)
        for (Type type : ancestors) {
            if (type instanceof Class<?>) {
                // ancestor is non-parameterized. Recurse only if it matches the base class.
                Class<?> ancestorClass = (Class<?>) type;
                if (base.isAssignableFrom(ancestorClass)) {
                    Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
                    if (result != null) {
                        return result;
                    }
                }
            }
            if (type instanceof ParameterizedType) {
                // ancestor is parameterized. Recurse only if the raw type matches the base class.
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?>) {
                    Class<?> rawTypeClass = (Class<?>) rawType;
                    if (base.isAssignableFrom(rawTypeClass)) {

                        // loop through all type arguments and replace type variables with the actually known types
                        List<Type> resolvedTypes = new LinkedList<Type>();
                        for (Type t : parameterizedType.getActualTypeArguments()) {
                            if (t instanceof TypeVariable<?>) {
                                Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
                                resolvedTypes.add(resolvedType != null ? resolvedType : t);
                            } else {
                                resolvedTypes.add(t);
                            }
                        }

                        Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[]{}));
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }

        // we have a result if we reached the base class.
        return offspring.equals(base) ? actualArgs : null;
    }

    private void resolveTypeParameters(Class type){
        final ParameterizedType genericSuperType = (ParameterizedType) type.getGenericSuperclass();
        genericSuperType.getActualTypeArguments();
        final TypeVariable[] typeVariables = type.getTypeParameters();
//        for(TypeVariable tv : typeVariables){
//            tv.get
//        }
    }
    
    //@Test
    public void testGenericMethod() throws NoSuchMethodException {
        Method method = User.class.getMethod("getAccount");
        LOG.info(method.toGenericString());


        final TypeResolver typeResolver = new TypeResolver();
        final Class managerType = (Class) typeResolver.resolveType(Manager.class);
        method = managerType.getMethod("getAccount");
        LOG.info(method.toGenericString());
        
        final TypeToken<Manager> managerTypeToken = TypeToken.of(Manager.class);
        final Invokable<Manager, Object> invokable = managerTypeToken.method(method);
        final TypeVariable<?>[] typeParameters = invokable.getTypeParameters();
        
        
        

        final Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof TypeVariable) {
            final TypeVariable manager_getAccountType = (TypeVariable) method.getGenericReturnType();
            final String name = manager_getAccountType.getName();
            final Type genericSuperclass = Manager.class.getGenericSuperclass();
            final Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            Object o =  Manager.class.getSuperclass().getTypeParameters();
            LOG.info(o.toString());
            
        }
        method = Manager.class.getMethod("getAccount");
        method.getGenericReturnType();
        final TypeVariable manager_getAccountType = (TypeVariable) method.getGenericReturnType();
        LOG.info(manager_getAccountType.toString());
    }
}
