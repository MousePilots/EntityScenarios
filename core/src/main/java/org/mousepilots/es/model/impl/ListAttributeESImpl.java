package org.mousepilots.es.model.impl;

import java.util.List;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.ListAttributeES;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-11-2015
 * @param <T> The type the represented List belongs to.
 * @param <E> The element type of the represented List.
 */
public class ListAttributeESImpl<T, E>
    extends PluralAttributeESImpl<T, List<E>, E>
    implements ListAttributeES<T, E> {   

    public ListAttributeESImpl(Type<E> elementType,
            BindableParameters<E> bindableParameters,
            AttributeTypeParameters<List<E>> attributeTypeParameters,
            PersistentAttributeType persistentAttributeType, MemberES javaMember,
            boolean readOnly, boolean collection, boolean association,
            ManagedTypeES declaringType) {
        super(CollectionType.LIST, elementType, bindableParameters,
                attributeTypeParameters, persistentAttributeType, javaMember,
                readOnly, collection, association, declaringType);
    }
}