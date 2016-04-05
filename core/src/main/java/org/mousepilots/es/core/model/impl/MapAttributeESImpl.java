package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.mousepilots.es.core.command.attribute.MapObserverImpl;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.ObservableMap;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Map belongs to
 * @param <K> The type of the key of the represented Map
 * @param <V> The type of the value of the represented Map
 */
public final class MapAttributeESImpl<T, K, V> extends PluralAttributeESImpl<T, Map<K, V>, V> implements MapAttributeES<T, K, V> {

    private final int keyTypeOrdinal;

    @Override
    protected Map<K, V> createObserved(Proxy<T> proxy, Map<K, V> value) {
        ObservableMap<K, V> retval = new ObservableMap<>();
        retval.addListener(new MapObserverImpl(proxy, this));
        return retval;
    }

    public MapAttributeESImpl(
            String name,
            int ordinal,
            int typeOrdinal,
            int declaringTypeOrdinal,
            Integer superOrdinal,
            Collection<Integer> subOrdinals,
            PersistentAttributeType persistentAttributeType,
            PropertyMember<T, Map<K, V>> javaMember,
            AssociationES valueAssociation,
            int elementTypeOrdinal,
            int keyTypeOrdinal,
            AssociationES keyAssociation) {
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation, elementTypeOrdinal);
        this.keyTypeOrdinal = keyTypeOrdinal;
        this.associations.put(AssociationTypeES.KEY, keyAssociation);
    }

    @Override
    public Class<K> getKeyJavaType() {
        return getKeyType().getJavaType();
    }

    @Override
    public TypeES<K> getKeyType() {
        return AbstractMetamodelES.getInstance().getType(keyTypeOrdinal);
    }

    @Override
    public String toString() {
        return StringUtils.createToString(getClass(), Arrays.asList(
                "name", getName(),
                "ordinal", getOrdinal(),
                "javaType", getJavaType().getName() + "<" + getKeyType().getJavaType().getName() + "," + getElementType().getJavaType().getName() + ">"
        ));
    }

}
