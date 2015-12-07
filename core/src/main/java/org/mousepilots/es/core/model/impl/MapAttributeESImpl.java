package org.mousepilots.es.core.model.impl;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.mousepilots.es.core.model.DtoType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.TypeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Map belongs to
 * @param <K> The type of the key of the represented Map
 * @param <V> The type of the value of the represented Map
 */
public class MapAttributeESImpl<T, K, V>
        extends PluralAttributeESImpl<T, Map<K, V>, V, Collection<Entry<K,V>>>
        implements MapAttributeES<T, K, V> {

    private final Class<K> keyJavaType;
    private final TypeES<K> keyType;
    private final Constructor<HasValue> hasValueKeyChangeConstructor, hasValueKeyDtoConstructor;

    @Override
    public Map<K, V> createEmpty() {
        return new HashMap<>();
    }

    /**
     * Create a new instance of this class.
     * @param keyJavaType the java type that represents the keys of this map attribute.
     * @param keyType the {@link TypeES} that represents the keys of this map attribute.
     * @param elementType the type of the elements for this map attribute.
     * @param bindableType the {@link BindableType} of this map attribute.
     * @param bindableJavaType the java type that is bound for this map attribute.
     * @param name the name of this map attribute.
     * @param ordinal the ordinal of this map attribute.
     * @param javaType the java type of this map attribute.
     * @param persistentAttributeType the {@link PersistentAttributeType} of this map attribute.
     * @param javaMember the java {@link Member} representing this map attribute.
     * @param readOnly whether or not this map attribute is read only.
     * @param association whether or not this map attribute is part of an association.
     * @param declaringType the {@link ManagedTypeES} that declared this map attribute.
     * @param hasValueKeyChangeConstructor The constructor that will be used for wrapping the key of this attribute for a change.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     * @param hasValueDtoConstructor the constructor that will be used when wrapping this attribute for a DTO.
     * @param hasValueKeyDtoConstructor the constructor that will be used when wrapping the key of this attribute for a DTO.
     */
    public MapAttributeESImpl(Class<K> keyJavaType, TypeES<K> keyType,
            Constructor<HasValue> hasValueKeyChangeConstructor,
            Constructor<HasValue> hasValueKeyDtoConstructor,
            TypeES<V> elementType, BindableType bindableType,
            Class<V> bindableJavaType, String name, int ordinal,
            Class<Map<K, V>> javaType,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean association, ManagedTypeES<T> declaringType,
            Constructor<HasValue> hasValueChangeConstructor,
            Constructor<HasValue> hasValueDtoConstructor) {
        super(CollectionType.MAP, elementType, bindableType, bindableJavaType,
                name, ordinal, javaType, persistentAttributeType, javaMember,
                readOnly, association, declaringType, hasValueChangeConstructor,
                hasValueDtoConstructor);
        this.keyJavaType = keyJavaType;
        this.keyType = keyType;
        this.hasValueKeyChangeConstructor = hasValueKeyChangeConstructor;
        this.hasValueKeyDtoConstructor = hasValueKeyDtoConstructor;
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
    public final HasValueList wrapForChange(Collection<Entry<K,V>> values, DtoType dtoType) {
        dtoType.assertSupported();
        final Constructor<HasValue> hasValueChangeConstructor = getHasValueChangeConstructor();
        ArrayList<HasValueEntry> hasValueEntries = new ArrayList<>(values.size());
        final TypeES<V> valueType = getElementType();
        for(Entry<K,V> entry : values){
            final HasValue keyHV = hasValueKeyChangeConstructor.invoke();
            SingularAttributeESImpl.setSingularValueForChange(keyHV, keyType, entry.getKey());
            final HasValue valueHV = hasValueChangeConstructor.invoke();
            SingularAttributeESImpl.setSingularValueForChange(valueHV, valueType, entry.getValue());
            HasValueEntry hve = new HasValueEntry(keyHV, valueHV);
            hasValueEntries.add(hve);
        }
        final HasValueList retval = new HasValueList();
        retval.setValue(hasValueEntries);
        return retval;
    }

    @Override
    public String toString() {
        return super.toString() + " MapAttribute keyType: "
                + getKeyType().getJavaClassName();
    }

    @Override
    public boolean isCollection() {
        return true;
    }
}