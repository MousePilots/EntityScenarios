package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.collection.Observable;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 7-12-2015
 * @param <T> The type the represented collection belongs to
 * @param <C> The type of the represented collection
 * @param <E> The element type of the represented collection
 */
public abstract class PluralAttributeESImpl<T, C, E> extends AttributeESImpl<T, C> implements PluralAttributeES<T, C, E> {
    
    private final int elementTypeOrdinal;

    protected PluralAttributeESImpl(
            String name, 
            int ordinal, 
            int typeOrdinal, 
            int declaringTypeOrdinal, 
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            PersistentAttributeType persistentAttributeType, 
            PropertyMember<T,C> javaMember, 
            AssociationES valueAssociation, 
            int elementTypeOrdinal){
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation);
        this.elementTypeOrdinal = elementTypeOrdinal;
    }

    @Override
    public final TypeES<E> getElementType() {
        return AbstractMetamodelES.getInstance().getType(elementTypeOrdinal);
    }

    @Override
    public final BindableType getBindableType() {
        return BindableType.PLURAL_ATTRIBUTE;
    }

    @Override
    public final Class<E> getBindableJavaType() {
        return getElementType().getJavaType();
    }
    
    protected abstract C createObserved(Proxy<T> proxy, C value);
    
    public C getObserved(Proxy<T> proxy, C value){
        if(value instanceof Observable){
            return value;
        } else {
            return createObserved(proxy, value);
        }
    }

 

     @Override
     public boolean isCollection() {
          return true;
     }
    
    @Override
    public String toString() {
        return StringUtils.createToString(getClass(), Arrays.asList(
                "name",     getName(),
                "ordinal",  getOrdinal(),
                "javaType", getJavaType().getName() + "<" + getElementType().getJavaType().getName() + ">"
        ));
    }
    
    
}
