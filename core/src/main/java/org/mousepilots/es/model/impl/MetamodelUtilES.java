package org.mousepilots.es.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.mousepilots.es.model.TypeES;

/**
 * @author Roy Cleven
 */
public class MetamodelUtilES {

    public static <T> Collection<TypeES<? super T>> getSuperTypes(Class<? super T> baseClass) {
        Collection<TypeES<? super T>> supers;
        supers = new ArrayList<>();

        Class<? super T> superclass = baseClass;

        do {
            superclass = superclass.getSuperclass();
            //use superclass to create TypeES then add to supers
        } while (superclass != Object.class);

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractTypeES.class.getName()).log(Level.SEVERE, 
                    null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbstractTypeES.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
        return null;
    }

    public static boolean isInstantiable(Class<?> clazz) {
        return Stream.of(clazz.getConstructors())
                .anyMatch((c) -> c.getParameterCount() == 0);
    }
}
