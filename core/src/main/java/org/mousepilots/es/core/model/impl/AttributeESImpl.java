package org.mousepilots.es.core.model.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <X> the represented type that contains the attribute.
 * @param <Y> the type of the represented attribute.
 */
public abstract class AttributeESImpl<X, Y> implements AttributeES<X, Y> {

    private final String name;
    private final int ordinal, typeOrdinal, declaringTypeOrdinal;
    private final Integer superOrdinal;
    private final SortedSet<Integer> subOrdinals = new TreeSet<>();
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final PropertyMember<X,Y> javaMember;
    protected final Map<AssociationTypeES, AssociationES<X,?>> associations = new EnumMap<>(AssociationTypeES.class);

    protected AttributeESImpl(
            String name,
            int ordinal, 
            int typeOrdinal,
            int declaringTypeOrdinal,
            Integer superOrdinal, 
            Collection<Integer> subOrdinals, 
            PersistentAttributeType persistentAttributeType, 
            PropertyMember<X,Y> javaMember, 
            AssociationES valueAssociation){
        this.name = name;
        this.ordinal = ordinal;
        this.typeOrdinal = typeOrdinal;
        this.declaringTypeOrdinal = declaringTypeOrdinal;
        this.superOrdinal = superOrdinal;
        if (subOrdinals != null) {
            this.subOrdinals.addAll(subOrdinals);
        }
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        if(valueAssociation!=null){
            this.associations.put(AssociationTypeES.VALUE, valueAssociation);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int getOrdinal() {
        return ordinal;
    }

    @Override
    public final TypeES<Y> getType() {
        return AbstractMetamodelES.getInstance().getType(typeOrdinal);
    }

    public final AttributeES getSuperAttribute() {
        return this.superOrdinal == null ? null : AbstractMetamodelES.getInstance().getAttribute(superOrdinal);
    }

    public final SortedSet< ? extends AttributeES> getSubAttributes() {
        TreeSet retval = new TreeSet<>();
        final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
        for (Integer subOrdinal : subOrdinals) {
            retval.add(metamodel.getAttribute(subOrdinal));
        }
        return retval;
    }

    @Override
    public final Class<Y> getJavaType() {
        return getType().getJavaType();
    }

    @Override
    public final PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public final MemberES<X, Y> getJavaMember() {
        return javaMember;
    }

    @Override
    public final boolean isReadOnly() {
        return this.javaMember.isReadOnly();
    }

    @Override
    public final boolean isAssociation() {
        return !this.associations.isEmpty();
    }

    @Override
    public final ManagedTypeES<X> getDeclaringType() {
        final TypeES declaringType = AbstractMetamodelES.getInstance().getType(declaringTypeOrdinal);
        return (ManagedTypeES<X>) declaringType;
    }

    @Override
    public final Map<AssociationTypeES, AssociationES<X,?>> getAssociations() {
        return Collections.unmodifiableMap(associations);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(getName());
        hash = 83 * hash + Objects.hashCode(getJavaType());
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        //all instances are singletons
        return this == obj;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return associations.containsKey(type);
    }

    @Override
    public AssociationES<X,?> getAssociation(AssociationTypeES type) {
        return associations.get(type);
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    @Override
    public String toString() {
        return StringUtils.createToString(
                getClass(),
                Arrays.asList(
                        "name", name,
                        "ordinal", ordinal,
                        "type", getJavaType()
                ));
    }
}
