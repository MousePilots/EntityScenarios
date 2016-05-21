/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.core.util.Producer;

/**
 *
 * @author AP34WV
 */
public class ValueCreator {

    private final int pluralAttributeSize;
    private final EntityManagerES entityManager = new EntityManagerFactoryImpl().createEntityManager();
    private final Map<Class,Producer> basicValueProducers = new HashMap();
    private short generator = 0;
    private final int maxManagedTypeInstances;
    private int managedTypeInstances;
    private short generate(){
        return (short)(generator++ % Short.MAX_VALUE);
    }
    
    private <T> Producer<T> getBasicValueProducer(Class<T> basicJavaType){
        final Producer retval = basicValueProducers.get(basicJavaType);
        if(retval==null){
            throw new IllegalStateException("cannot generate value of " + basicJavaType);
        }
        return retval;
    }
    
    public ValueCreator(int maxManagedTypeInstances, int pluralAttributeSize) {
        this.maxManagedTypeInstances = maxManagedTypeInstances;
        this.pluralAttributeSize = pluralAttributeSize;
        basicValueProducers.put(String.class,       () -> String.valueOf(generate()));
        basicValueProducers.put(char[].class,       () -> String.valueOf(generate()).toCharArray());
        basicValueProducers.put(Date.class,         () -> new Date(generate()));
        
        basicValueProducers.put(Boolean.class,      () -> (generate()%2)==0 ? true : false);
        basicValueProducers.put(Byte.class,         () -> (byte)(generate() % Byte.MAX_VALUE) );
        basicValueProducers.put(Short.class,        () -> generate());
        basicValueProducers.put(Character.class,    () -> (char)generate() );
        basicValueProducers.put(Integer.class,      () -> Integer.valueOf(generate()));
        basicValueProducers.put(Long.class,         () -> Long.valueOf(generate()));
        basicValueProducers.put(Float.class,        () -> Float.valueOf(generate()));
        basicValueProducers.put(Double.class,       () -> Double.valueOf(generate()));
        basicValueProducers.put(BigInteger.class,   () -> BigInteger.valueOf(generate()));
        basicValueProducers.put(BigDecimal.class,   () -> BigDecimal.valueOf(generate()));
        
        basicValueProducers.put(boolean.class,      () -> basicValueProducers.get(Boolean.class).produce());
        basicValueProducers.put(byte.class,         () -> basicValueProducers.get(Byte.class).produce());
        basicValueProducers.put(short.class,        () -> basicValueProducers.get(Short.class).produce());
        basicValueProducers.put(char.class,         () -> basicValueProducers.get(Character.class).produce());
        basicValueProducers.put(int.class,          () -> basicValueProducers.get(Integer.class).produce());
        basicValueProducers.put(long.class,         () -> basicValueProducers.get(Long.class).produce());
        basicValueProducers.put(float.class,        () -> basicValueProducers.get(Float.class).produce());
        basicValueProducers.put(double.class,       () -> basicValueProducers.get(Double.class).produce());
    }
    
    
    private final AttributeVisitor<Object,Void> attributeValueCreator = new AttributeVisitor<Object, Void>() {
        @Override
        public Object visit(SingularAttributeES a, Void arg) {
            if(a.isId() || a.isVersion() || a.isReadOnly()){
                return null;
            } else {
                return a.getType().accept(typeValueCreator, null);
            }
        }
        
        private Object visitCollection(PluralAttributeES collectionAttribute){
            final Collection collection = (Collection) collectionAttribute.createEmpty();
            final TypeES elementType = collectionAttribute.getElementType();
            for(int i=0; i<pluralAttributeSize; i++){
                final Object element = elementType.accept(typeValueCreator, null);
                if(element==null){
                    return null;
                } else {
                    collection.add(element);
                }
            }
            return collection;
        }

        @Override
        public Object visit(CollectionAttributeES a, Void arg) {
            return visitCollection(a);
        }

        @Override
        public Object visit(ListAttributeES a, Void arg) {
            return visitCollection(a);
        }

        @Override
        public Object visit(SetAttributeES a, Void arg) {
            return visitCollection(a);
        }

        @Override
        public Object visit(MapAttributeES a, Void arg) {
            final Map map = a.createEmpty();
            final TypeES keyType = a.getKeyType();
            final TypeES elementType = a.getElementType();
            for(int i=0; i<pluralAttributeSize; i++){
                final Object key = keyType.accept(typeValueCreator, null);
                final Object value = elementType.accept(typeValueCreator, null);
                if(key!=null && value!=null){
                    map.put(key, value);
                } else {
                    return null;
                }
            }
            return map;
        }
    };
    
    private final TypeVisitor<Object,Void> typeValueCreator = new TypeVisitor<Object, Void>() {
        @Override
        public Object visit(BasicTypeES t, Void arg) {
            return getBasicValueProducer(t.getJavaType()).produce();
        }
        
        private Object visitManagedType(ManagedTypeES t){
            if(managedTypeInstances==maxManagedTypeInstances){
                return null;
            } else {
                final List<ManagedTypeES> instantiableSubTypes = new LinkedList<>();
                for(ManagedTypeES subType : (Set<ManagedTypeES>) t.getSubTypes()){
                    if(subType.isInstantiable()){
                        instantiableSubTypes.add(subType);
                    }
                }
                if(instantiableSubTypes.isEmpty()){
                    return null;
                } else {
                    final ManagedTypeES subType = instantiableSubTypes.get(generate() % instantiableSubTypes.size());
                    final Object subTypeInstance = subType.createInstance();
                    for(AttributeESImpl attribute : (Set<AttributeESImpl>) subType.getAttributes()){
                        final Object value = attribute.accept(attributeValueCreator, null);
                        if(value!=null){
                            attribute.getJavaMember().set(subTypeInstance, value);
                        }
                    }
                    managedTypeInstances++;
                    return subTypeInstance;
                }
            }
        }

        @Override
        public Object visit(EmbeddableTypeES t, Void arg) {
            return visitManagedType(t);
        }

        @Override
        public Object visit(MappedSuperclassTypeES t, Void arg) {
            return visitManagedType(t);
        }

        @Override
        public Object visit(EntityTypeES t, Void arg) {
            return visitManagedType(t);
        }
    };
    
    public <T> T create(TypeES<T> type){
        return (T) type.accept( typeValueCreator, null);
    }
    
}
