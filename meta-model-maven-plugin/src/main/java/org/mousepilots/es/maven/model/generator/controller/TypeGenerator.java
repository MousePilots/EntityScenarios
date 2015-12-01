package org.mousepilots.es.maven.model.generator.controller;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.mousepilots.es.maven.model.generator.model.type.BasicTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EmbeddableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EntityTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.MappedSuperClassDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 19-11-2015
 */
public class TypeGenerator {

    private final AtomicInteger ordinal = new AtomicInteger(0);

    private static final Comparator<Class> CLASS_COMPARATOR = new Comparator<Class>() {

        @Override
        public int compare(Class o1, Class o2) {
            return o1.getCanonicalName().compareTo(o2.getCanonicalName());
        }
    };

    private static final Map<Class<? extends PluralAttribute>, Class> PLURAL_ATTRIBUTE_TO_COLLECTION_CLASS;

    static {
        Map<Class<? extends PluralAttribute>, Class> pluralAttributeToCollectionClass = new HashMap<>();
        pluralAttributeToCollectionClass.put(CollectionAttribute.class, Collection.class);
        pluralAttributeToCollectionClass.put(ListAttribute.class, List.class);
        pluralAttributeToCollectionClass.put(SetAttribute.class, Set.class);
        pluralAttributeToCollectionClass.put(MapAttribute.class, Map.class);
        PLURAL_ATTRIBUTE_TO_COLLECTION_CLASS = Collections.unmodifiableMap(pluralAttributeToCollectionClass);
    }

    /**
     *
     * @param jpaMetaModelClasses the set of classes on the classpath annotated
     * with {@link StaticMetamodel}
     * @return a descriptor for each
     */
    public SortedSet<TypeDescriptor> generate(Set<Class<?>> jpaMetaModelClasses) {
        final SortedSet<TypeDescriptor> retval = new TreeSet<>();
        addManagedTypeDescriptors(retval, jpaMetaModelClasses);

        return retval;
    }

    /**
     * Add all of the managed type descriptors to the {@code typeDescriptors}.
     *
     * @param typeDescriptors The set to put the created managed type
     * descriptors into.
     * @param jpaMetaModelClasses The set of Java classes to create the
     * descriptors for.
     * @throws IllegalStateException The persistence type cannot be determined.
     */
    private void addManagedTypeDescriptors(final SortedSet<TypeDescriptor> typeDescriptors, Set<Class<?>> jpaMetaModelClasses) throws IllegalStateException {
        final TreeSet<Class> sortedClasses = new TreeSet<>(CLASS_COMPARATOR);
        sortedClasses.addAll(jpaMetaModelClasses);
        for (Class<?> managedClass : sortedClasses) {
            final Class javaType = managedClass.getAnnotation(StaticMetamodel.class).value();
            final TypeDescriptor td;
            if (javaType.getAnnotation(Embeddable.class) != null) {
                //embeddable
                td = new EmbeddableTypeDescriptor(javaType.getSimpleName(), javaType, ordinal.getAndIncrement());
            } else if (javaType.getAnnotation(MappedSuperclass.class) != null) {
                //mapped superclass
                td = new MappedSuperClassDescriptor(javaType.getSimpleName(), javaType, ordinal.getAndIncrement());

            } else if (javaType.getAnnotation(Entity.class) != null) {
                td = new EntityTypeDescriptor(javaType.getSimpleName(), javaType, ordinal.getAndIncrement());
            } else {
                throw new IllegalStateException("unable to determine PersistenceType for " + managedClass);
            }
            typeDescriptors.add(td);
        }
    }

    /**
     * Get the generic types for a certain field.
     *
     * @param field The {@link Field} to get the generic types of.
     * @param indices The indices for the generic type parameters to get.
     * @return An array of {@link Class}es with the found generic types.
     */
    private Class[] getGenericTypes(Field field, int... indices) {
        final Class[] retval = new Class[indices.length];
        final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        final Type[] actualTypeArguments = genericType.getActualTypeArguments();
        int i = 0;
        for (int index : indices) {
            final int realIndex = index < 0 ? actualTypeArguments.length + index : index;
            retval[i++] = (Class) actualTypeArguments[realIndex];
        }

        return retval;
    }

    /**
     * Add all of the basic type descriptors to the {@code managedTypeDescriptors}.
     * @param managedTypeDescriptors The set of managed type descriptors determined earlier.
     * @param jpaMetaModelClasses The set of meta model classes to create the basic type descriptors from.
     * @throws IllegalStateException If the persistence type could not be determined.
     */
    private void addBasicTypeDescriptors(final SortedSet<TypeDescriptor> managedTypeDescriptors, Set<Class<?>> jpaMetaModelClasses) throws IllegalStateException {
        SortedSet<BasicTypeDescriptor> basicTypeDescriptors = new TreeSet<>();
        for (Class<?> metaModelClass : jpaMetaModelClasses) {
            for (Field metaModelField : metaModelClass.getDeclaredFields()) {
                final int[] indices;
                if (metaModelField.getType() == MapAttribute.class) {
                    indices = new int[]{-1, -2};
                } else {
                    indices = new int[]{-1};
                }
                final Class[] attributeJavaTypes = getGenericTypes(metaModelField, indices);
                for (Class attributeJavaType : attributeJavaTypes) {
                    final TypeDescriptor td = TypeDescriptor.getInstance(attributeJavaType);
                    if (td == null) {
                        basicTypeDescriptors.add(
                                new BasicTypeDescriptor(attributeJavaType.getSimpleName(), attributeJavaType, ordinal.getAndIncrement())
                        );
                    }
                }
                final Class<?> metaModelFieldType = metaModelField.getType();
                if (PluralAttribute.class.isAssignableFrom(metaModelFieldType)) {
                    final Class javaFieldType = PLURAL_ATTRIBUTE_TO_COLLECTION_CLASS.get(metaModelFieldType);
                    //TODO cast
                    final TypeDescriptor td = TypeDescriptor.getInstance(javaFieldType);
                    if (td == null) {
                        //TODO aanmaken TypeDescriptor
                    }
                }
            }
        }
        managedTypeDescriptors.addAll(basicTypeDescriptors);
    }
}