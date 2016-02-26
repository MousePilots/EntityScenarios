package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented Map belongs to
 * @param <K> The type of the key of the represented Map
 * @param <V> The type of the value of the represented Map
 */
public class MapAttributeESImpl<T, K, V> extends PluralAttributeESImpl<T, Map<K, V>, V> implements MapAttributeES<T, K, V> {

     private final int keyTypeOrdinal;
     private final Constructor<HasValue> hasValueKeyChangeConstructor;

     @Override
     public Map<K, V> createEmpty() {
          return new HashMap<>();
     }

     public MapAttributeESImpl(
          String name,
          int ordinal,
          Integer superOrdinal,
          Collection<Integer> subOrdinals,
          int typeOrdinal,
          PersistentAttributeType persistentAttributeType,
          MemberES javaMember,
          boolean readOnly,
          AssociationES keyAssociation,
          AssociationES valueAssociation,
          ManagedTypeES<T> declaringType,
          Constructor<HasValue> hasValueChangeConstructor,
          CollectionType collectionType,
          int keyTypeOrdinal,
          int elementTypeOrdinal,
          BindableType bindableType,
          Class<V> bindableJavaType,
          Constructor<HasValue> hasValueKeyChangeConstructor) {
          super(name, ordinal, superOrdinal, subOrdinals, typeOrdinal, persistentAttributeType, javaMember, readOnly, valueAssociation, declaringType, hasValueChangeConstructor, collectionType, elementTypeOrdinal, bindableType, bindableJavaType);
          this.associations.put(AssociationTypeES.KEY, keyAssociation);
          this.keyTypeOrdinal = keyTypeOrdinal;
          this.hasValueKeyChangeConstructor = hasValueKeyChangeConstructor;
     }

     
     @Override
     public Class<K> getKeyJavaType() {
          return getKeyType().getJavaType();
     }

     @Override
     public TypeES<K> getKeyType() {
          return AbstractMetamodelES.getInstance().getType(keyTypeOrdinal);
     }

     public Constructor<HasValue> getHasValueKeyChangeConstructor() {
          return hasValueKeyChangeConstructor;
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
