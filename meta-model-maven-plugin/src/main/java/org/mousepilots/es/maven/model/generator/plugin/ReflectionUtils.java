package org.mousepilots.es.maven.model.generator.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;

/**
 * Utility class that provides a lot of methods to help with reflection tasks.
 * @author Nicky Ernste
 * @author Jurjen van Geenen
 * @version 1.0, 12-11-2015
 */
public class ReflectionUtils
{
   private ReflectionUtils()
   {
   }

   private static final Map<Class< ? extends PluralAttributeES>, Class> PLURAL_ATTRIBUTE_CLASS_TO_PROPERTY_TYPE;

   static
   {
      Map<Class< ? extends PluralAttributeES>, Class> m = new HashMap<>();
      m.put(CollectionAttributeES.class, Collection.class);
      m.put(ListAttributeES.class, List.class);
      m.put(MapAttributeES.class, Map.class);
      m.put(SetAttributeES.class, Set.class);
      PLURAL_ATTRIBUTE_CLASS_TO_PROPERTY_TYPE = Collections.unmodifiableMap(m);
   }

   /**
    * Gets the associated {@link Map} or {@link Collection} (sub-)class associated to a subclass of {@link PluralAttribute}.
    * @param pluralAttributeClass
    * @return the associated {@link Map} or {@link Collection} (sub-)class
    */
   public static final Class getPluralAttributePropertyType(Class< ? extends PluralAttributeES> pluralAttributeClass)
   {
      return PLURAL_ATTRIBUTE_CLASS_TO_PROPERTY_TYPE.get(pluralAttributeClass);
   }

//   public static final Class getAssociationTargetClass(Attribute.AccessPoint ap, Class jpaMetaModelOwnerClass, String attributeName)
//   {
//      final Field metaModelField = findField(jpaMetaModelOwnerClass, attributeName);
//      final Type genericType = metaModelField.getGenericType();
//      final ParameterizedType parameterizedType = (ParameterizedType) genericType;
//      final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//      if (MapAttribute.class.isAssignableFrom(metaModelField.getType()))
//      {
//         return (Class) actualTypeArguments[ap == Attribute.AccessPoint.KEY ? 1 : 2];
//      }
//      else if (ap == Attribute.AccessPoint.KEY)
//      {
//         throw new IllegalArgumentException(jpaMetaModelOwnerClass.getName() + "." + attributeName + " is no map");
//      }
//      else
//      {
//         return (Class) actualTypeArguments[1];
//      }
//   }

   /**
    * Find a certain {@link Field} in a {@link Class} by it's name.
    * Also searches super classes if the field could not be found in the specified class.
    * @param clazz The {@link Class} to search for the specified field name.
    * @param name The name of the field to look for.
    * @return The {@link Field} that has the specified name, or {@code null} if the field could not be found.
    */
   public static Field findField(Class clazz, String name)
   {
      try
      {
         return clazz.getDeclaredField(name);
      }
      catch (NoSuchFieldException ex)
      {
         final Class scf = clazz.getSuperclass();
         if (scf == null || scf == Object.class)
         {
            return null;
         }
         else
         {
            return findField(scf, name);
         }
      }
   }

   /**
    * Find a specific {@link Field} for the specified {@link Class} that is annotated with the specified {@link Annotation}.
    * Also checks the super classes if the field could not be found in the specified class.
    * @param clazz The class to get the annotated field for.
    * @param annotation The annotation to check for on the specified class.
    * @return The {@link Field} that is annotated with the specified annotation, or {@code null} if no field matching the criteria could be found.
    */
   public static Field findField(Class clazz, Class< ? extends Annotation> annotation)
   {
      for (Field field : clazz.getDeclaredFields())
      {
         if (field.getAnnotation(annotation) != null)
         {
            return field;
         }
      }
      final Class superClass = clazz.getSuperclass();
      if (superClass == null)
      {
         return null;
      }
      else
      {
         return findField(superClass, annotation);
      }
   }

   /**
    * Check if a class contains a certain annotation.
    * @param managedTypeClass The {@link Class} to check for the specified annotation.
    * @param annotationClass The annotation {@link Class} to check for.
    * @return {@code true} If the specified annotation was found, {@code false} otherwise.
    */
   public static boolean containsAnnotation(Class managedTypeClass, Class< ? extends Annotation> annotationClass)
   {
      return managedTypeClass.getAnnotation(annotationClass) != null;
   }

   /**
    * Get the name of a certain reflection {@link Type}
    * @param t The {@link Type} to get the name for.
    * @return The name of the specified {@link Type}.
    * @throws UnsupportedOperationException If the specified {@link Type} is not an instance of {@link Class}.
    */
   public static String getTypeName(Type t)
   {
      if (t instanceof Class)
      {
         return ((Class) t).getName();
      }
      else
         throw new UnsupportedOperationException("cannot handle " + t + " of java.reflection.Type sub-class " + t.getClass());
   }

   /**
    * Get a generic string for the property type of a meta model class.
    * @param metaModelFieldOwner The meta model class that is owner of the field.
    * @param name The name of the field to get the property type string for.
    * @return A string with the name of the class and the name of the generic types.
    */
   public static String getGenericPropertyTypeString(Class metaModelFieldOwner, String name)
   {
      Field field = findField(metaModelFieldOwner, name);
      final Type metaModelField = field.getGenericType();
      final ParameterizedType parameterizedType = (ParameterizedType) metaModelField;
      final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

      final String retval;
      if (MapAttribute.class.isAssignableFrom(field.getType()))
      {
         retval = Map.class.getName() + "<" + getTypeName(actualTypeArguments[1]) + "," + getTypeName(actualTypeArguments[2]) + ">";
      }
      else if (ListAttribute.class.isAssignableFrom(field.getType()))
      {
         retval = List.class.getName() + "<" + getTypeName(actualTypeArguments[1]) + ">";
      }
      else if (SetAttribute.class.isAssignableFrom(field.getType()))
      {
         retval = Set.class.getName() + "<" + getTypeName(actualTypeArguments[1]) + ">";
      }
      else if (CollectionAttribute.class.isAssignableFrom(field.getType()))
      {
         retval = Collection.class.getName() + "<" + getTypeName(actualTypeArguments[1]) + ">";
      }
      else
      {
         retval = getTypeName(actualTypeArguments[1]);
      }
      return retval.replace("$", ".");

   }

   /**
    * Get the class for a type of a meta model field.
    * @param metaModelField The meta model field to get the type for.
    * @return The class of the type for the specified meta model field.
    */
   public static Class getPropertyType(Field metaModelField)
   {
      if (MapAttributeES.class.isAssignableFrom(metaModelField.getType()))
      {
         return Map.class;
      }
      else if (ListAttributeES.class.isAssignableFrom(metaModelField.getType()))
      {
         return List.class;
      }
      else if (SetAttributeES.class.isAssignableFrom(metaModelField.getType()))
      {
         return Set.class;
      }
      else if (CollectionAttributeES.class.isAssignableFrom(metaModelField.getType()))
      {
         return Collection.class;
      }
      else
      {
         final Type genericType = metaModelField.getGenericType();
         final ParameterizedType parameterizedType = (ParameterizedType) genericType;
         final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
         return (Class) actualTypeArguments[1];
      }
   }

   /**
    * Returns the name of a property type from a meta model field.
    * @param metaModelField The field of the meta model to get the name for.
    * @return The name of the type for the specified meta model field.
    */
   public static String getPropertyTypeName(Field metaModelField)
   {
      return getPropertyType(metaModelField).getName().replace("$", ".");
   }

   /**
     * Get all the super types of the specified class as a collection.
     * @param <T> The type of class to get the super types for.
     * @param baseClass The actual class to get the super types for.
     * @return A collection of {@link TypeES} implementations that represent
     * the super types of the specified class.
     * Or an empty collection if there are no super types.
     */
    public static <T> Collection<TypeES<? super T>> getSuperTypes(
            Class<? super T> baseClass) {
        //TODO finish implementation.
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
     * Note! An instance can only be created of classes that have a default
     * no-arg constructor.
     * Use the {@link isInstantiable()} method to check if an instance can be created.
     * @param <T> The type of the specified class.
     * @param clazz The actual class to get an instance of.
     * @return An instance of the specified class or null if an instance could
     * not be created.
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
     * This is checked by seeing of the class contains any parameterless constructors.
     * @param clazz The actual class to check.
     * @return {@code true} If the specified class is instantiable,
     * {@code false} if it's not.
     */
    public static boolean isInstantiable(Class<?> clazz) {
        //TODO check if constructor is public or protected.
        //TODO check if class is concrete.
        return Stream.of(clazz.getConstructors())
                .anyMatch((c) -> c.getParameterCount() == 0);
    }
}