package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented type.
 */
public class ManagedTypeESImpl<T> extends TypeESImpl<T>
        implements ManagedTypeES<T> {

    private final SortedSet<PluralAttribute<? super T, ?, ?>> pluralAttributes = new TreeSet<>();
    private final SortedSet<PluralAttribute<T, ?, ?>> declaredPluralAttributes = new TreeSet<>();
    private final SortedSet<Attribute<? super T, ?>> attributes = new TreeSet<>();
    private final SortedSet<Attribute<T, ?>> declaredAttributes = new TreeSet<>();
    private final SortedSet<SingularAttribute<? super T, ?>> singularAttributes = new TreeSet<>();
    private final SortedSet<SingularAttribute<T, ?>> declaredSingularAttributes = new TreeSet<>();
    private final SortedSet<CollectionAttributeES<? super T, ?>> collectionAttributes = new TreeSet<>();
    private final SortedSet<CollectionAttributeES<T, ?>> declaredCollectionAttributes = new TreeSet<>();
    private final SortedSet<ListAttributeES<? super T, ?>> listAttributes = new TreeSet<>();
    private final SortedSet<ListAttributeES<T, ?>> declaredListAttributes = new TreeSet<>();
    private final SortedSet<SetAttributeES<? super T, ?>> setAttributes = new TreeSet<>();
    private final SortedSet<SetAttributeES<T, ?>> declaredSetAttributes = new TreeSet<>();
    private final SortedSet<MapAttributeES<? super T, ?, ?>> mapAttributes = new TreeSet<>();
    private final SortedSet<MapAttributeES<T, ?, ?>> declaredMapAttributes = new TreeSet<>();

    /**
     * Create a new instance of this class.
     *
     * @param attributes the singular attributes that are part of this managed
     * type.
     * @param name the name of this managed type.
     * @param ordinal the ordinal of this managed type.
     * @param javaType the java type for this managed type.
     * @param persistenceType the {@link PersistenceType} for this managed type.
     * @param javaClassName the name of the java class that represents this
     * managed type.
     * @param instantiable whether or not this managed type is instanciable.
     * @param metamodelClass the JPa meta model class for this managed type.
     * @param superType the supertype of this managed type.
     * @param subTypes a set of sub types for this managed type.
     */
    public ManagedTypeESImpl(
            String name,
            int ordinal,
            Class<T> javaType,
            PersistenceType persistenceType,
            String javaClassName,
            boolean instantiable,
            Class<?> metamodelClass,
            Set<Integer> attributes,
            int superType,
            Collection<Integer> subTypes) {
        super(name, ordinal, javaType, persistenceType, javaClassName, instantiable, metamodelClass, superType, subTypes);
        getAttributesFromMetaModel(attributes);
        sortAndFillAttributes();
    }

    @Override
    public SortedSet<Attribute<? super T, ?>> getAttributes() {
        return attributes;
    }

    @Override
    public SortedSet<Attribute<T, ?>> getDeclaredAttributes() {
        return declaredAttributes;
    }

    @Override
    public <Y> SingularAttributeES<? super T, Y> getSingularAttribute(
            String name, Class<Y> type) {
        Set<SingularAttribute<? super T, ?>> singularAttributeSet
                = this.singularAttributes;
        for (SingularAttribute<? super T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<? super T, Y>) att;
            }
        }
        return null;
    }

    @Override
    public <Y> SingularAttributeES<T, Y> getDeclaredSingularAttribute(
            String name, Class<Y> type) {
        Set<SingularAttribute<T, ?>> declaredSingularAttributeSet
                = this.declaredSingularAttributes;
        for (SingularAttribute<T, ?> att : declaredSingularAttributeSet) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<T, Y>) att;
            }
        }
        return null;
    }

    @Override
    public SortedSet<SingularAttribute<? super T, ?>> getSingularAttributes() {
        return singularAttributes;
    }

    @Override
    public SortedSet<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        return this.declaredSingularAttributes;
    }

    @Override
    public <E> CollectionAttributeES<? super T, E> getCollection(String name,
            Class<E> elementType) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributeSet
                = this.collectionAttributes;
        for (CollectionAttributeES<? super T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (CollectionAttributeES<? super T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <E> CollectionAttributeES<T, E> getDeclaredCollection(String name,
            Class<E> elementType) {
        Set<CollectionAttributeES<T, ?>> declaredCollectionAttributeSet
                = this.declaredCollectionAttributes;
        for (CollectionAttributeES<T, ?> att : declaredCollectionAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (CollectionAttributeES<T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <E> SetAttributeES<? super T, E> getSet(String name,
            Class<E> elementType) {
        Set<SetAttributeES<? super T, ?>> setAttributeSet
                = this.setAttributes;
        for (SetAttributeES<? super T, ?> att : setAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (SetAttributeES<? super T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <E> SetAttributeES<T, E> getDeclaredSet(String name,
            Class<E> elementType) {
        Set<SetAttributeES<T, ?>> declaredSetAttributeSet
                = this.declaredSetAttributes;
        for (SetAttributeES<T, ?> att : declaredSetAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (SetAttributeES<T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <E> ListAttributeES<? super T, E> getList(String name,
            Class<E> elementType) {
        Set<ListAttributeES<? super T, ?>> listAttributeSet
                = this.listAttributes;
        for (ListAttributeES<? super T, ?> att : listAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (ListAttributeES<? super T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <E> ListAttributeES<T, E> getDeclaredList(String name,
            Class<E> elementType) {
        Set<ListAttributeES<T, ?>> declaredListAttributeSet
                = this.declaredListAttributes;
        for (ListAttributeES<T, ?> att : declaredListAttributeSet) {
            if (att.getName().equals(name) && elementType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (ListAttributeES<T, E>) att;
            }
        }
        return null;
    }

    @Override
    public <K, V> MapAttributeES<? super T, K, V> getMap(String name,
            Class<K> keyType, Class<V> valueType) {
        Set<MapAttributeES<? super T, ?, ?>> mapAttributeSet
                = this.mapAttributes;
        for (MapAttributeES<? super T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name) && keyType == att.getKeyJavaType()
                    && valueType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (MapAttributeES<? super T, K, V>) att;
            }
        }
        return null;
    }

    @Override
    public <K, V> MapAttributeES<T, K, V> getDeclaredMap(String name,
            Class<K> keyType, Class<V> valueType) {
        Set<MapAttributeES<T, ?, ?>> mapAttributeSet
                = this.declaredMapAttributes;
        for (MapAttributeES<T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name) && keyType == att.getKeyJavaType()
                    && valueType == att.getElementType().getClass()) {
                //Not sure if this will fail at runtime.
                return (MapAttributeES<T, K, V>) att;
            }
        }
        return null;
    }

    @Override
    public SortedSet<PluralAttribute<? super T, ?, ?>> getPluralAttributes() {
        return pluralAttributes;
    }

    @Override
    public SortedSet<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes() {
        return declaredPluralAttributes;
    }

    @Override
    public Attribute<? super T, ?> getAttribute(String name) {
        Set<Attribute<? super T, ?>> attributeSet
                = this.attributes;
        for (Attribute<? super T, ?> att : attributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public Attribute<T, ?> getDeclaredAttribute(String name) {
        Set<Attribute<T, ?>> declaredAttributeSet
                = this.declaredAttributes;
        for (Attribute<T, ?> att : declaredAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<? super T, ?> getSingularAttribute(String name) {
        Set<SingularAttribute<? super T, ?>> singularAttributeSet
                = this.singularAttributes;
        for (SingularAttribute<? super T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SingularAttribute<T, ?> getDeclaredSingularAttribute(String name) {
        Set<SingularAttribute<T, ?>> singularAttributeSet
                = this.declaredSingularAttributes;
        for (SingularAttribute<T, ?> att : singularAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<? super T, ?> getCollection(String name) {
        Set<CollectionAttributeES<? super T, ?>> collectionAttributeSet
                = this.collectionAttributes;
        for (CollectionAttributeES<? super T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public CollectionAttributeES<T, ?> getDeclaredCollection(String name) {
        Set<CollectionAttributeES<T, ?>> collectionAttributeSet
                = this.declaredCollectionAttributes;
        for (CollectionAttributeES<T, ?> att : collectionAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<? super T, ?> getSet(String name) {
        Set<SetAttributeES<? super T, ?>> setAttributeSet
                = this.setAttributes;
        for (SetAttributeES<? super T, ?> att : setAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public SetAttributeES<T, ?> getDeclaredSet(String name) {
        Set<SetAttributeES<T, ?>> declaredSetAttributeSet
                = this.declaredSetAttributes;
        for (SetAttributeES<T, ?> att : declaredSetAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<? super T, ?> getList(String name) {
        Set<ListAttributeES<? super T, ?>> listAttributeSet
                = this.listAttributes;
        for (ListAttributeES<? super T, ?> att : listAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public ListAttributeES<T, ?> getDeclaredList(String name) {
        Set<ListAttributeES<T, ?>> declaredListAttributeSet
                = this.declaredListAttributes;
        for (ListAttributeES<T, ?> att : declaredListAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<? super T, ?, ?> getMap(String name) {
        Set<MapAttributeES<? super T, ?, ?>> mapAttributeSet
                = this.mapAttributes;
        for (MapAttributeES<? super T, ?, ?> att : mapAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    @Override
    public MapAttributeES<T, ?, ?> getDeclaredMap(String name) {
        Set<MapAttributeES<T, ?, ?>> declaredMapAttributeSet
                = this.declaredMapAttributes;
        for (MapAttributeES<T, ?, ?> att : declaredMapAttributeSet) {
            if (att.getName().equals(name)) {
                //Not sure if this will fail at runtime.
                return att;
            }
        }
        return null;
    }

    private void getAttributesFromMetaModel(Set<Integer> attributeOrdinals){
        for (int attributeOrdinal : attributeOrdinals) {
            attributes.add(AbstractMetaModelES.getInstance().getAttribute(attributeOrdinal));
        }
    }

    /**
     * Sort all the attributes of this managed type and fill the seperate lists.
     * The attributes are sorted by if they are a collection or a single attribute.
     * Then its determined if they are declared by this type or a different one.
     * Then they are added to the respective lists in this managed type.
     */
    private void sortAndFillAttributes() {
        for (Attribute attribute : getAttributes()) {
            if (attribute.isCollection()) {
                if (attribute.getClass().isAssignableFrom(PluralAttributeES.class)) {
                    PluralAttributeES plural = (PluralAttributeES) attribute;
                    if (attribute.getDeclaringType() != null && attribute.getDeclaringType().getClass().getName().equals(this.getClass().getName())) {
                        //Plural attribute is declared by this class.
                        declaredAttributes.add(attribute);
                        declaredPluralAttributes.add(plural);
                        switch (plural.getCollectionType()) {
                            case COLLECTION:
                                CollectionAttributeES collection = (CollectionAttributeES) plural;
                                declaredCollectionAttributes.add(collection);
                                break;
                            case LIST:
                                ListAttributeES list = (ListAttributeES) plural;
                                declaredListAttributes.add(list);
                                break;
                            case MAP:
                                MapAttributeES map = (MapAttributeES) plural;
                                declaredMapAttributes.add(map);
                                break;
                            case SET:
                                SetAttributeES set = (SetAttributeES) plural;
                                declaredSetAttributes.add(set);
                                break;
                            default:
                                throw new IllegalStateException("The collection type could not be determined.");
                        }
                    } else {
                        //Plural attribute is declared somewhere else.
                        pluralAttributes.add(plural);
                        switch (plural.getCollectionType()) {
                            case COLLECTION:
                                CollectionAttributeES collection = (CollectionAttributeES) plural;
                                collectionAttributes.add(collection);
                                break;
                            case LIST:
                                ListAttributeES list = (ListAttributeES) plural;
                                listAttributes.add(list);
                                break;
                            case MAP:
                                MapAttributeES map = (MapAttributeES) plural;
                                mapAttributes.add(map);
                                break;
                            case SET:
                                SetAttributeES set = (SetAttributeES) plural;
                                setAttributes.add(set);
                                break;
                            default:
                                throw new IllegalStateException("The collection type could not be determined.");
                        }
                    }
                }
            } else {
                if (attribute.getClass().isAssignableFrom(SingularAttributeES.class)) {
                    SingularAttributeES singular = (SingularAttributeES) attribute;
                    if (attribute.getDeclaringType() != null && attribute.getDeclaringType().getClass().getName().equals(this.getClass().getName())) {
                        //Singular attribute is declared by this class.
                        declaredAttributes.add(attribute);
                        declaredSingularAttributes.add(singular);
                    } else {
                        //Singular attribute is declared somewhere else.
                        singularAttributes.add(singular);
                    }
                }
            }
        }
    }
}