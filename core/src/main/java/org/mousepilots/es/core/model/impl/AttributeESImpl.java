package org.mousepilots.es.core.model.impl;

import java.lang.reflect.Member;
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
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.util.StringUtils;

/**
 * @author Nicky Ernste
 * @version 1.0, 18-12-2015
 * @param <X> the represented type that contains the attribute.
 * @param <Y> the type of the represented attribute.
 */
public abstract class AttributeESImpl<X, Y> implements AttributeES<X, Y> {

    protected final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);
    private final int ordinal;
    private final String name;
    private final Integer superOrdinal;
    private final SortedSet<Integer> subOrdinals = new TreeSet<>();
    private final int typeOrdinal;
    private final Attribute.PersistentAttributeType persistentAttributeType;
    private final MemberES javaMember;
    private final boolean readOnly;
    private final ManagedTypeES<X> declaringType;
    private final Constructor<HasValue> hasValueChangeConstructor;

    /**
     * Create a new instance of this class.
     * @param name the name of this attribute.
     * @param ordinal the ordinal of this attribute.
     * @param superOrdinal the value of superOrdinal
     * @param subOrdinals the value of subOrdinals
     * @param typeOrdinal  the ordinal of {@link #getType()}
     * @param persistentAttributeType the {@link PersistentAttributeType} of this attribute.
     * @param javaMember the java {@link Member} representing this attribute.
     * @param readOnly whether or not this attribute is read only.
     * @param valueAssociation the {@link AssociationTypeES#VALUE} association if any, otherwise {@code null}.
     * @param declaringType the {@link ManagedTypeES} that declared this attribute.
     * @param hasValueChangeConstructor the constructor that will be used when wrapping this attribute for a change.
     */
    public AttributeESImpl(
         String name, int ordinal, Integer superOrdinal, Collection<Integer> subOrdinals, int typeOrdinal, PersistentAttributeType persistentAttributeType, MemberES javaMember, boolean readOnly, AssociationES valueAssociation, ManagedTypeES<X> declaringType, Constructor<HasValue> hasValueChangeConstructor) {
        this.name = name;
        this.ordinal = ordinal;
        this.superOrdinal=superOrdinal;
        if(subOrdinals!=null){
            this.subOrdinals.addAll(subOrdinals);
        }
        this.typeOrdinal = typeOrdinal;
        this.persistentAttributeType = persistentAttributeType;
        this.javaMember = javaMember;
        this.readOnly = readOnly;
        this.associations.put(AssociationTypeES.VALUE, valueAssociation);
        this.declaringType = declaringType;
        this.hasValueChangeConstructor = hasValueChangeConstructor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

     @Override
     public TypeES<Y> getType() {
          return AbstractMetamodelES.getInstance().getType(typeOrdinal);
     }
    
    
    
    public AttributeES getSuperAttribute(){
        return this.superOrdinal==null ? null : AbstractMetamodelES.getInstance().getAttribute(superOrdinal);
    }
    
    public SortedSet< ? extends AttributeES> getSubAttributes(){
        TreeSet retval = new TreeSet<>();
        final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
        for(Integer subOrdinal : subOrdinals){
            retval.add(metamodel.getAttribute(subOrdinal));
        }
        return retval;
    }

    @Override
    public Class<Y> getJavaType() {
        return getType().getJavaType();
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistentAttributeType;
    }

    @Override
    public MemberES getJavaMember() {
        return javaMember;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public boolean isAssociation() {
        return !this.associations.isEmpty();
    }

    @Override
    public ManagedTypeES<X> getDeclaringType() {
        return declaringType;
    }

    @Override
    public Map<AssociationTypeES, AssociationES> getAssociations() {
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
    public final boolean equals(Object obj){
        //all instances are singletons
        return this==obj;
    }

    @Override
    public boolean isAssociation(AssociationTypeES type) {
        return associations.containsKey(type);
    }

    @Override
    public AssociationES getAssociation(AssociationTypeES type) {
        return associations.get(type);
    }

    @Override
    public int compareTo(AttributeES o) {
        return Integer.compare(getOrdinal(), o.getOrdinal());
    }

    /**
     * Get the constructor that is used when wrapping the attribute for a change.
     * @return The constructor for wrapping a change.
     */
    public Constructor<HasValue> getHasValueChangeConstructor() {
        return hasValueChangeConstructor;
    }

    @Override
    public String toString() {
        return StringUtils.createToString(
            getClass(), 
            Arrays.asList(
                "name",     name,
                "ordinal",  ordinal,
                "type", getJavaType()
            ));
    }
}