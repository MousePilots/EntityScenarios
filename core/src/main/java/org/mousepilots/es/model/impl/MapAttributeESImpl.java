package org.mousepilots.es.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.util.IdentifiableUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 20-11-2015
 * @param <T> The type the represented Map belongs to
 * @param <K> The type of the key of the represented Map
 * @param <V> The type of the value of the represented Map
 */
public class MapAttributeESImpl<T, K, V>
        extends PluralAttributeESImpl<T, Map<K, V>, V>
        implements MapAttributeES<T, K, V> {

    private final Class<K> keyJavaType;
    private final TypeES<K> keyType;

    @Override
    public Map<K, V> createEmpty() {
        return new HashMap<>();
    }

    public MapAttributeESImpl(Class<K> keyJavaType, TypeES<K> keyType, CollectionType collectionType, TypeES<V> elementType, BindableType bindableType, Class<V> bindableJavaType, String name, int ordinal, Class<Map<K, V>> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES<T> declaringType, Constructor<HasValue> hasValueChangeConstructor, Constructor<HasValue> hasValueDtoConstructor) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType, hasValueChangeConstructor, hasValueDtoConstructor);
        this.keyJavaType = keyJavaType;
        this.keyType = keyType;
    }

    @Override
    public Class<K> getKeyJavaType() {
        return keyJavaType;
    }

    @Override
    public TypeES<K> getKeyType() {
        return keyType;
    }

    @Override
    public HasValue wrapForChange(Map<K, V> values) {
        //Implementation for the Map.
        final HasValue retval = getHasValueChangeConstructor().invoke();
        Set<HasValueEntry<K, V>> hasValueEntries = fillHasValueEntries(values);
        for (HasValueEntry hasValueEntry : hasValueEntries) {
            ManagedTypeES<? extends Object> managedType = AbstractMetaModelES.getInstance().managedType(hasValueEntry.getValue().getValue().getClass());
            switch (managedType.getPersistenceType()) {
                case ENTITY:
                    final IdentifiableTypeESImpl<V> iet = (IdentifiableTypeESImpl) getElementType();
                    final Collection ids = IdentifiableUtils.addIds(iet, values.values(), new ArrayList<V>());
                    retval.setValue(ids);
                    return retval;
                case BASIC:
                case EMBEDDABLE:
                    retval.setValue(values);
                    return retval;
                default:
                    throw new IllegalStateException("Bad attribute type for " + this + ": " + managedType.getPersistenceType());
            }
        }
        return null;
    }

    private Set<HasValueEntry<K, V>> fillHasValueEntries(Map<K, V> values) {
        Set<HasValueEntry<K, V>> hasValueEntries = new HashSet<>();
        for (Entry entry : values.entrySet()) {
            final HasValue key = getHasValueChangeConstructor().invoke();
            final HasValue value = getHasValueChangeConstructor().invoke();
            key.setValue(entry.getKey());
            value.setValue(entry.getValue());
            HasValueEntry<K, V> hve = new HasValueEntry<>(key, value);
            hasValueEntries.add(hve);
        }
        return hasValueEntries;
    }
}