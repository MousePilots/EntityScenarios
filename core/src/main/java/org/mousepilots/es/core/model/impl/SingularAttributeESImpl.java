package org.mousepilots.es.core.model.impl;

import java.util.Collection;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.Generator;
import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <X> The type containing the represented wrapAttribute
 * @param <Y> The type of the represented wrapAttribute
 */
public final class SingularAttributeESImpl<X, Y> extends AttributeESImpl<X, Y> implements SingularAttributeES<X, Y> {

    private final Generator generator;
    private final boolean id;
    private final boolean version;
    private final boolean optional;

    public SingularAttributeESImpl(
            String name,
            int ordinal, 
            int typeOrdinal,
            int declaringTypeOrdinal,
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            PersistentAttributeType persistentAttributeType, 
            PropertyMember<X,Y> javaMember, 
            AssociationES valueAssociation,            
            Generator generator,
            boolean id,
            boolean version,
            boolean optional){
        super(name, ordinal, typeOrdinal, declaringTypeOrdinal, superOrdinal, subOrdinals, persistentAttributeType, javaMember, valueAssociation);
        this.generator = generator;
        this.id = id;
        this.version = version;
        this.optional = optional;
    }

    @Override
    public boolean isGenerated() {
        return generator!=null;
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public BindableType getBindableType() {
        return BindableType.SINGULAR_ATTRIBUTE;
    }

    @Override
    public Class<Y> getBindableJavaType() {
        return getJavaType();
    }

    @Override
    public boolean isId() {
        return id;
    }

    @Override
    public boolean isVersion() {
        return version;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public boolean isCollection() {
        return false;
    }
}