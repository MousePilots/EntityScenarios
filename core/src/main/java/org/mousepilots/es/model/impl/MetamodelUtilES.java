package org.mousepilots.es.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.mousepilots.es.model.TypeES;

/**
 * Utilities class that contains common implementations for the meta model interfaces.
 * @author Roy Cleven
 * @version 3-11-2015
 */
public class MetamodelUtilES {

    /**
     * Get all the super types of the specified class as a collection.
     * @param <T> The type of class to get the super types for.
     * @param baseClass The actual class to get the super types for.
     * @return A collection of {@link TypeES} implementations that represent the super types of the specified class.
     * Or an empty collection if there are no super types.
     */
    public static <T> Collection<TypeES<? super T>> getSuperTypes(Class<? super T> baseClass) {
        Collection<TypeES<? super T>> supers;
        supers = new ArrayList<>();

        Class<? super T> superclass = baseClass;

        do {
            superclass = superclass.getSuperclass();

        } while (superclass != Object.class);

        return supers;
    }

    /**
     * Try to create an instance of the specified class.
     * Note! An instance can only be created of classes that have a default no-arg constructor.
     * @param <T> The type of the specified class.
     * @param clazz The actual class to get an instance of.
     * @return An instance of the specified class or null if an instance could not be created.
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(TypeESImpl.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }

    /**
     * Check if the specified class is instantiable.
     * @param clazz The actual class to check.
     * @return {@code true} If the specified class is instantiable, {@code false} if it's not.
     */
    public static boolean isInstantiable(Class<?> clazz) {
        return Stream.of(clazz.getConstructors())
                .anyMatch((c) -> c.getParameterCount() == 0);
    }
}
