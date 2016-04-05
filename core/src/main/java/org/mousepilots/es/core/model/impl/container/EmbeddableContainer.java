/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.container;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.impl.MapAttributeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.StringUtils;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 * Wraps an embeddable {@link #container} of an associated embeddable {@code e}, such that the association is via {@link #getAttribute()}.
 * @author geenenju
 * @param <E>
 */
public final class EmbeddableContainer<E> extends Container<E,EmbeddableTypeES<E>,AttributeES< ? super E,?>> {
     
    private Container parent;

    private HasValue<E> container;
    
    private EmbeddableContainer(){
        super();
    }


    public EmbeddableContainer(EmbeddableTypeES<E> embeddableType, AttributeES< ? super E,?> attribute, E embeddable) {
        super(embeddableType,attribute);
        EmbeddableTypeESImpl t = (EmbeddableTypeESImpl) embeddableType;
        this.container = t.wrap(embeddable);
    }
    
    public E getEmbeddable(){
        return container.getValue();
    }
    
    @Override
    public Container copy(){
         final EmbeddableTypeESImpl<E> type = (EmbeddableTypeESImpl<E>) getType();
         final E embeddable = getEmbeddable();
         final E copy = type.copy(embeddable);
         final EmbeddableContainer<E> retval = new EmbeddableContainer<>(type, getAttribute(), copy);
         retval.setParent(parent.copy());
         final Object mapKey = getMapKey();
         if(mapKey!=null){
              final MapAttributeESImpl attribute = (MapAttributeESImpl) getAttribute();
              final TypeESImpl keyType = (TypeESImpl) attribute.getKeyType();
              final Object mapKeyCopy = keyType.accept(MAP_KEY_COPIER, mapKey);
              retval.setMapKey(mapKeyCopy);
         }
         return retval;
    }
    

    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public Container getParent() {
        return parent;
    }


    @Override @GwtIncompatible
    public E resolve(ServerContext serverContext)
    {
        final Iterator<Container> i = collectPath().iterator();
        //lookup the first item in the container-path, which is identifiable by design
        Container currentContainer = i.next();
        Object managedInstance = currentContainer.resolve(serverContext);
        do{
            final EmbeddableContainer nextContainer = (EmbeddableContainer) i.next();
            managedInstance = EmbeddableLocator.locate(currentContainer, managedInstance, nextContainer.getEmbeddable());
            currentContainer = nextContainer;
            
        } while(currentContainer!=this);
        return (E) managedInstance;
        
    }
    
    
    @Override
    public String toString() {
        return StringUtils.createToString(
            getClass(),
            Arrays.asList(
                "type", getType(),
                "embeddable", container.getValue()
            )
        );
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.container.getValue());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmbeddableContainer<?> other = (EmbeddableContainer<?>) obj;
        return Objects.equals(this.container, other.container);
    }
}
