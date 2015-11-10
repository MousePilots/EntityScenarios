package org.mousepilots.es.model.impl;

import java.util.Set;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SetAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented Set belongs to
 * @param <E> The element type of the represented Set
 */
public class SetAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, Set<E>, E>implements SetAttributeES<T, E> {  

    public SetAttributeESImpl(Type<E> elementType,
            BindableParameters<E> bindableParameters,
            AttributeTypeParameters<Set<E>> attributeTypeParameters,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES declaringType) {
        super(CollectionType.SET, elementType, bindableParameters,
                attributeTypeParameters, persistentAttributeType, javaMember,
                readOnly, collection, association, declaringType);
    }
}