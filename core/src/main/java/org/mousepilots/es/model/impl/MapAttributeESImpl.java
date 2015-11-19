package org.mousepilots.es.model.impl;

import java.util.Map;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MapAttributeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 * @param <T> The type the represented Map belongs to
 * @param <K> The type of the key of the represented Map
 * @param <V> The type of the value of the represented Map
 */
public class MapAttributeESImpl<T, K, V>
    extends PluralAttributeESImpl<T, Map<K, V>, V>
    implements MapAttributeES<T, K, V> {

    private final Class<K> keyJavaType;
    private final TypeES<K> keyType;

    public MapAttributeESImpl(Class<K> keyJavaType, TypeES<K> keyType, CollectionType collectionType, TypeES<V> elementType, BindableType bindableType, Class<V> bindableJavaType, String name, int ordinal, Class<Map<K, V>> javaType, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, boolean collection, boolean association, ManagedTypeES declaringType) {
        super(collectionType, elementType, bindableType, bindableJavaType, name, ordinal, javaType, persistentAttributeType, javaMember, readOnly, collection, association, declaringType);
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
}