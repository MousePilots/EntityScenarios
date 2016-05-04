package org.mousepilots.es.core.model.impl.container;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Locates the managed equivalent of a detached embeddable in the server-side {@link EntityManager}.
 * 
 */
public class EmbeddableLocator {
    
    /**
     * Locates the managed equivalent {@code mt} of a detached embeddable {@code detachedTarget}, being the target of an association between the entity or embeddable {@code managedSource}.
     * The corresponding association has source attribute {@code container.getAttribute()}.
     * Said attribute represents an association.
     * @param <E>
     * @param container the container corresponding to the {@code managedOwner}
     * @param managedSource the managed entity or embeddable from which the association to the {@code detachedEmbeddable} originates
     * @param detachedTarget the detached target of the association from the {@code managedOwner}
     * @return 
     * @throws NullPointerException if {@code me} cannot be found
     */
    public static <E> E locate(Container container, Object managedSource, E detachedTarget) throws NullPointerException{
        final EmbeddableLocator embeddableLocator = new EmbeddableLocator(container, detachedTarget);
        final Object retval = container.getAttribute().accept(embeddableLocator.attributeVisitor, managedSource);
        return (E) retval;
    }

    private final Container container;

    private final Object embeddable;
    
    private Object locate(final Collection embeddables, Object embeddable) {
        if (embeddables != null) {
            for (Object e : embeddables) {
                if (Objects.equals(e, embeddable)) {
                    return e;
                }
            }
        }
        return null;
    }

    private <X,Y> Object locate(X managedContainer) throws IllegalStateException {
        final AttributeES<? super X,? extends Collection<Y>> containerAttribute = container.getAttribute();
        final Collection<Y> embeddables = containerAttribute.getJavaMember().get(managedContainer);
        final Object managedEmbeddable = locate(embeddables, embeddable);
        if (managedEmbeddable == null) {
            throw new NullPointerException("cannot find persistent equivalent of " + embeddable + " in attribute " + containerAttribute + " of " + managedContainer);
        } else {
            return managedEmbeddable;
        }
    }
    
    private final AttributeVisitor<Object, Object> attributeVisitor = new AttributeVisitor<Object, Object>() {

        @Override
        public Object visit(SingularAttributeES containerAttribute, Object managedOwner){
            return containerAttribute.getJavaMember().get(managedOwner);
        }

        @Override
        public Object visit(CollectionAttributeES containerAttribute, Object managedOwner){
            return locate(managedOwner);
        }

        @Override
        public Object visit(ListAttributeES containerAttribute, Object managedOwner){
            return locate(managedOwner);
        }

        @Override
        public Object visit(SetAttributeES containerAttribute, Object managedOwner){
            return locate(managedOwner);
        }

        @Override
        public Object visit(MapAttributeES containerAttribute, Object managedOwner){
            final Map mapAttributeValue = (Map) containerAttribute.getJavaMember().get(managedOwner);
            if (mapAttributeValue == null) {
                throw new NullPointerException(containerAttribute + " on " + managedOwner + " is null");
            } else {
                final Object parentKey = container.getMapKey();
                if (parentKey == null) {
                    //embeddable.getValue() is a key
                    final Object key = embeddable;
                    final Object managedKey = locate(mapAttributeValue.keySet(), key);
                    if (managedKey == null) {
                        throw new NullPointerException("key " + key + " not found in keyset of " + containerAttribute + " on " + managedOwner);
                    } else {
                        return managedKey;
                    }
                } else {
                    //embeddable.getValue() is a value mapped by parentKey
                    final Object value = mapAttributeValue.get(parentKey);
                    final Object embeddableValue = embeddable;
                    if (Objects.equals(value, embeddableValue)) {
                        return value;
                    } else {
                        throw new IllegalStateException("non-existent mapping " + parentKey + "-->" + embeddableValue + " in attribute " + containerAttribute + " on " + managedOwner);
                    }
                }
            }
        }
    };
    

    private EmbeddableLocator(Container container, Object embeddable) {
        this.container = container;
        this.embeddable = embeddable;
    }

    


}
