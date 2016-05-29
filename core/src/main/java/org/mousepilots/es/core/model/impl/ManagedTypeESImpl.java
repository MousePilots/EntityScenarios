package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.CollectionUtils;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Jurjen van Geenen
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <T> The represented type.
 */
public abstract class ManagedTypeESImpl<T> extends TypeESImpl<T> implements ManagedTypeES<T> {

    private final Class<?> metamodelClass;
    private final Class<? extends Proxy<T>> proxyType;
    private final Constructor<T> javaTypeConstructor;
    private final Getter<? super T, Set<String>> getOwners;
    private final Constructor<? extends Proxy<T>> proxyTypeConstructor;

    private final SortedSet<Integer> attributeOrdinals = new TreeSet<>(), associationOrdinals = new TreeSet<>();
    private final SortedSet<PluralAttributeES<? super T, ?, ?>> pluralAttributes = new TreeSet<>();
    private final SortedSet<PluralAttributeES<T, ?, ?>> declaredPluralAttributes = new TreeSet<>();
    private final SortedSet<AttributeES<? super T, ?>> attributes = new TreeSet<>();
    private final SortedSet<AttributeES<T, ?>> declaredAttributes = new TreeSet<>();
    private final SortedSet<SingularAttributeES<? super T, ?>> singularAttributes = new TreeSet<>();
    private final SortedSet<SingularAttributeES<? super T, ?>> fullyAccessibleSingularBasicAttributes = new TreeSet<>();
    private final SortedSet<SingularAttributeES<T, ?>> declaredSingularAttributes = new TreeSet<>();
    private final SortedSet<CollectionAttributeES<? super T, ?>> collectionAttributes = new TreeSet<>();
    private final SortedSet<CollectionAttributeES<T, ?>> declaredCollectionAttributes = new TreeSet<>();
    private final SortedSet<ListAttributeES<? super T, ?>> listAttributes = new TreeSet<>();
    private final SortedSet<ListAttributeES<T, ?>> declaredListAttributes = new TreeSet<>();
    private final SortedSet<SetAttributeES<? super T, ?>> setAttributes = new TreeSet<>();
    private final SortedSet<SetAttributeES<T, ?>> declaredSetAttributes = new TreeSet<>();
    private final SortedSet<MapAttributeES<? super T, ?, ?>> mapAttributes = new TreeSet<>();
    private final SortedSet<MapAttributeES<T, ?, ?>> declaredMapAttributes = new TreeSet<>();

    private final Map<String, AttributeES<? super T, ?>> nameToAttribute = new HashMap<>();
    private final Map<String, AttributeES<? super T, ?>> nameToDeclaredAttribute = new HashMap<>();

    private final AttributeVisitor<Void, Void> attributeRegistrar = new AttributeVisitor<Void, Void>() {

        private void registerDefault(AttributeES attribute, Set declaredAttributeTypeSpecificSet, Set attributeTypeSpecificSet) throws IllegalStateException {
            if (isDeclared(attribute)) {
//                AbstractMetamodelES.ensureAdded(declaredAttributes, attribute);
                nameToDeclaredAttribute.put(attribute.getName(), attribute);
                CollectionUtils.ensureAdded(declaredAttributeTypeSpecificSet, attribute);
            } else {
                CollectionUtils.ensureAdded(attributes, attribute);
            }
            CollectionUtils.ensureAdded(attributeTypeSpecificSet, attribute);
            final AttributeES existing = nameToAttribute.put(attribute.getName(), attribute);
            if (existing != null) {
                throw new IllegalStateException("duplicate name for attributes " + existing + ", " + attribute);
            }
        }

        private void registerPlural(PluralAttributeES attribute) throws IllegalStateException {
            if (isDeclared(attribute)) {
                CollectionUtils.ensureAdded(declaredPluralAttributes, attribute);
            }
            CollectionUtils.ensureAdded(pluralAttributes, attribute);
        }

        @Override
        public Void visit(SingularAttributeES a, Void arg) {
            registerDefault(a, declaredSingularAttributes, singularAttributes);
            if(a.getType() instanceof BasicTypeES && !a.isReadOnly()){
                CollectionUtils.ensureAdded(fullyAccessibleSingularBasicAttributes, a);
            }
            return null;
        }

        @Override
        public Void visit(CollectionAttributeES a, Void arg) {
            registerDefault(a, declaredCollectionAttributes, collectionAttributes);
            registerPlural(a);
            return null;
        }

        @Override
        public Void visit(ListAttributeES a, Void arg) {
            registerDefault(a, declaredListAttributes, listAttributes);
            registerPlural(a);
            return null;
        }

        @Override
        public Void visit(SetAttributeES a, Void arg) {
            registerDefault(a, declaredSetAttributes, setAttributes);
            registerPlural(a);
            return null;
        }

        @Override
        public Void visit(MapAttributeES a, Void arg) {
            registerDefault(a, declaredMapAttributes, mapAttributes);
            registerPlural(a);
            return null;
        }
    };

    /**
     * Create a new instance of this class.
     *
     * @param ordinal the ordinal of this managed type.
     * @param javaType the java type for this managed type.
     * @param javaTypeConstructor the zero-arg constructor for the {@code javaType} if existent, otherwise {@code null}
     * @param getOwners 
     * @param proxyType the {@link Proxy}-type for the {@code javaType}
     * @param proxyTypeConstructor the zero-arg constructor for the {@code proxyType} if existent, otherwise {@code null}
     * @param hasValueConstructor the value of hasValueConstructor
     * @param metamodelClass the JPa meta model class for this managed type.
     * @param attributeOrdinals the singular attributes that are part of this managed type.
     * @param superTypeOrdinal the supertype of this managed type.
     * @param subTypeOrdinals a set of sub types for this managed type.
     * @param declaredAttributes
     * @param associationOrdinals
     */
    public ManagedTypeESImpl(
            int ordinal,
            Class<T> javaType,
            Class<?> metamodelClass,
            Integer superTypeOrdinal,
            Collection<Integer> subTypeOrdinals,
            Constructor<? extends HasValue<? super T>> hasValueConstructor,
            Constructor<T> javaTypeConstructor,
            Getter<? super T, Set<String>> getOwners,
            Constructor<? extends Proxy<T>> proxyTypeConstructor,
            Class<? extends Proxy<T>> proxyType,
            Collection<Integer> attributeOrdinals,
            Collection<AttributeES<T, ?>> declaredAttributes,
            Collection<Integer> associationOrdinals) {
        super(ordinal, javaType, superTypeOrdinal, subTypeOrdinals, hasValueConstructor);
        this.javaTypeConstructor = javaTypeConstructor;
        this.getOwners = getOwners;
        this.proxyTypeConstructor = proxyTypeConstructor;
        this.proxyType = proxyType;
        this.metamodelClass = metamodelClass;
        this.attributeOrdinals.addAll(attributeOrdinals);
        this.attributes.addAll(declaredAttributes);
        this.declaredAttributes.addAll(declaredAttributes);
        this.associationOrdinals.addAll(associationOrdinals);
    }

    @Override
    protected final void init(int round){
        switch(round){
            case 0 : {
                for(AttributeES declaredAttribute : declaredAttributes){
                    declaredAttribute.accept(attributeRegistrar, null);
                }
                break;
            }
            case 1 : {   
                //calculate attribute ordinals declared by strict superclasses only
                final Set<Integer> strictSuperClassAttributeOrdinals = new HashSet<>(attributeOrdinals);
                for (AttributeES a : declaredAttributes) {
                    strictSuperClassAttributeOrdinals.remove(a.getOrdinal());
                }
                //register
                final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
                for (int superClassAttributeOrdinal : strictSuperClassAttributeOrdinals) {
                    metamodel.getAttribute(superClassAttributeOrdinal).accept(attributeRegistrar, null);
                }
                break;
            }
        }

    }

    public final boolean isDeclared(AttributeES attribute) {
        return this.equals(attribute.getDeclaringType());
    }

    @Override
    public final SortedSet<Attribute<? super T, ?>> getAttributes() {
        return (SortedSet) attributes;
    }

    @Override
    public final SortedSet<Attribute<T, ?>> getDeclaredAttributes() {
        return (SortedSet) declaredAttributes;
    }

    @Override
    public final <Y> SingularAttributeES<? super T, Y> getSingularAttribute(String name, Class<Y> type) {
        final AttributeES a = nameToAttribute.get(name);
        return a.getJavaType() == type && a instanceof SingularAttributeES ? (SingularAttributeES<? super T, Y>) a : null;
    }

    @Override
    public final <Y> SingularAttributeES<T, Y> getDeclaredSingularAttribute(String name, Class<Y> type) {
        for (SingularAttribute<T, ?> att : this.declaredSingularAttributes) {
            if (att.getName().equals(name) && type == att.getJavaType()) {
                //Not sure if this will fail at runtime.
                return (SingularAttributeES<T, Y>) att;
            }
        }
        return null;
    }

    @Override
    public final SortedSet<SingularAttribute<? super T, ?>> getSingularAttributes() {
        return (SortedSet) Collections.unmodifiableSortedSet(singularAttributes);
    }

    public final SortedSet<SingularAttributeES<? super T, ?>> getFullyAccessibleSingularBasicAttributes() {
        return Collections.unmodifiableSortedSet(fullyAccessibleSingularBasicAttributes);
    }
    
    @Override
    public final SortedSet<SingularAttribute<T, ?>> getDeclaredSingularAttributes() {
        return (SortedSet) Collections.unmodifiableSortedSet(this.declaredSingularAttributes);
    }

    private <E, P extends PluralAttributeES<? super T, ?, ?>, R> P find(Set<P> attributes, String name, Class<E> elementType) {
        for (P attribute : attributes) {
            if (attribute.getName().equals(name)
                    && (elementType == null || attribute.getElementType().getJavaType() == elementType)) {
                return attribute;
            }
        }
        return null;
    }

    private <K, V, M extends MapAttributeES<? super T, ?, ?>> M find(Set<M> attributes, String name, Class<K> keyType, Class<V> elementType) {
        for (M attribute : attributes) {
            if (attribute.getName().equals(name)
                    && (keyType == null || attribute.getKeyJavaType() == keyType)
                    && (elementType == null || attribute.getElementType().getJavaType() == elementType)) {
                return attribute;
            }
        }
        return null;
    }

    @Override
    public final <E> CollectionAttributeES<? super T, E> getCollection(String name, Class<E> elementType) {
        return (CollectionAttributeES) find(this.collectionAttributes, name, elementType);
    }

    @Override
    public <E> CollectionAttributeES<T, E> getDeclaredCollection(String name, Class<E> elementType) {
        return (CollectionAttributeES) find(this.declaredCollectionAttributes, name, elementType);
    }

    @Override
    public <E> SetAttributeES<? super T, E> getSet(String name, Class<E> elementType) {
        return (SetAttributeES) find(this.setAttributes, name, elementType);
    }

    @Override
    public <E> SetAttributeES<T, E> getDeclaredSet(String name, Class<E> elementType) {
        return (SetAttributeES) find(this.declaredSetAttributes, name, elementType);
    }

    @Override
    public <E> ListAttributeES<? super T, E> getList(String name, Class<E> elementType) {
        return (ListAttributeES) find(this.listAttributes, name, elementType);
    }

    @Override
    public <E> ListAttributeES<T, E> getDeclaredList(String name, Class<E> elementType) {
        return (ListAttributeES) find(this.declaredListAttributes, name, elementType);
    }

    @Override
    public <K, V> MapAttributeES<? super T, K, V> getMap(String name, Class<K> keyType, Class<V> valueType) {
        return (MapAttributeES<? super T, K, V>) find(mapAttributes, name, keyType, valueType);
    }

    @Override
    public <K, V> MapAttributeES<T, K, V> getDeclaredMap(String name, Class<K> keyType, Class<V> valueType) {
        return (MapAttributeES<T, K, V>) find(declaredMapAttributes, name, keyType, valueType);
    }

    @Override
    public SortedSet<PluralAttribute<? super T, ?, ?>> getPluralAttributes() {
        return (SortedSet) Collections.unmodifiableSortedSet(pluralAttributes);
    }

    @Override
    public SortedSet<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes() {
        return (SortedSet) Collections.unmodifiableSortedSet(declaredPluralAttributes);
    }

    @Override
    public AttributeES<? super T, ?> getAttribute(String name) {
        return this.nameToAttribute.get(name);
    }

    @Override
    public AttributeES<T, ?> getDeclaredAttribute(String name) {
        return (AttributeES<T, ?>) this.nameToDeclaredAttribute.get(name);
    }

    @Override
    public SingularAttributeES<? super T, ?> getSingularAttribute(String name) {
        final AttributeES<? super T, ?> attribute = this.getAttribute(name);
        return attribute instanceof SingularAttributeES ? (SingularAttributeES<? super T, ?>) attribute : null;
    }

    @Override
    public SingularAttributeES<T, ?> getDeclaredSingularAttribute(String name) {
        final AttributeES<? super T, ?> attribute = this.getDeclaredAttribute(name);
        return attribute instanceof SingularAttributeES ? (SingularAttributeES<T, ?>) attribute : null;
    }

    @Override
    public CollectionAttributeES<? super T, ?> getCollection(String name) {
        final AttributeES<? super T, ?> attribute = this.getAttribute(name);
        return attribute instanceof CollectionAttributeES ? (CollectionAttributeES<T, ?>) attribute : null;
    }

    @Override
    public CollectionAttributeES<T, ?> getDeclaredCollection(String name) {
        final AttributeES<? super T, ?> attribute = this.getDeclaredAttribute(name);
        return attribute instanceof CollectionAttributeES ? (CollectionAttributeES<T, ?>) attribute : null;
    }

    @Override
    public SetAttributeES<? super T, ?> getSet(String name) {
        final AttributeES<? super T, ?> attribute = this.getAttribute(name);
        return attribute instanceof SetAttributeES ? (SetAttributeES<? super T, ?>) attribute : null;
    }

    @Override
    public SetAttributeES<T, ?> getDeclaredSet(String name) {
        final AttributeES<? super T, ?> attribute = this.getDeclaredAttribute(name);
        return attribute instanceof SetAttributeES ? (SetAttributeES<T, ?>) attribute : null;
    }

    @Override
    public ListAttributeES<? super T, ?> getList(String name) {
        final AttributeES<? super T, ?> attribute = this.getAttribute(name);
        return attribute instanceof ListAttributeES ? (ListAttributeES<? super T, ?>) attribute : null;
    }

    @Override
    public ListAttributeES<T, ?> getDeclaredList(String name) {
        final AttributeES<? super T, ?> attribute = this.getDeclaredAttribute(name);
        return attribute instanceof ListAttributeES ? (ListAttributeES<T, ?>) attribute : null;
    }

    @Override
    public MapAttributeES<? super T, ?, ?> getMap(String name) {
        final AttributeES<? super T, ?> attribute = this.getAttribute(name);
        return attribute instanceof MapAttributeES ? (MapAttributeES<? super T, ?, ?>) attribute : null;
    }

    @Override
    public MapAttributeES<T, ?, ?> getDeclaredMap(String name) {
        final AttributeES<? super T, ?> attribute = this.getDeclaredAttribute(name);
        return attribute instanceof MapAttributeES ? (MapAttributeES<T, ?, ?>) attribute : null;
    }

    @Override
    public boolean isInstantiable() {
        return javaTypeConstructor != null;
    }

    @Override
    public final T createInstance() {
        if (isInstantiable()) {
            return javaTypeConstructor.invoke();
        } else {
            throw new UnsupportedOperationException(getJavaType() + "is not concrete or has no accessible zero arg constructor");
        }
    }
    
    @Override
    public final boolean supportsHasOwners(){
        return this.getOwners!=null;
    }
    
    @Override
    public final Set<String> getOwners(T instance) throws UnsupportedOperationException{
        if(supportsHasOwners()){
            return this.getOwners.invoke(instance);
        } else {
            throw new UnsupportedOperationException(this.getJavaType() + " does not provide means to determine the owner(s) of an instance");
        }
    }

    @Override
    public final Class<? extends Proxy<T>> getProxyJavaType() {
        return proxyType;
    }

    @Override
    public final Proxy<T> createProxy() {
        if (isInstantiable()) {
            return proxyTypeConstructor.invoke();
        } else {
            throw new UnsupportedOperationException(getJavaType() + "is not concrete or has no accessible zero arg constructor");
        }
    }

    /**
     * Creates a shallow copy of {@code instance} with only simple, singular attribute values copied. NB: attribute values are not cloned but referenced instead.
     *
     * @param instance the instance to be copied
     * @return a shallow clone of {@code instance} with only simple, singular attribute values copied
     */
    public T shallowClone(T instance) {
        final T shallowClone = this.createInstance();
        for (SingularAttributeES<? super T, ?> singularAttribute : singularAttributes) {
            if(!singularAttribute.isAssociation()) {
                final MemberES javaMember = singularAttribute.getJavaMember();
                final Object simpleAttributeValue = javaMember.get(instance);
                javaMember.set(shallowClone, simpleAttributeValue);
            }
        }
        return shallowClone;
    }

    @Override
    public final Class getMetamodelClass() {
        return metamodelClass;
    }
    
    public abstract int hash(Proxy<T> proxy);
    
    public final String toString(Proxy<T> proxy){
        final T subject = proxy.__subject();
        return getProxyJavaType().getSimpleName() + "[" + StringUtils.join(this.fullyAccessibleSingularBasicAttributes, a -> a.getName() + "=" + a.getJavaMember().get(subject), ", ") + "]";
    }

}
