package org.mousepilots.es.model.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.Generator;
import org.mousepilots.es.model.ManagedTypeES;
import org.mousepilots.es.model.MemberES;
import org.mousepilots.es.model.SingularAttributeES;

/**
 * @author Nicky Ernste
 * @version 1.0, 3-11-2015
 */
public class SingularAttributeESImpl<X, T> implements SingularAttributeES<X, T> {

    private final boolean generated;
    private final Generator generator;
    private final boolean readOnly;
    private final boolean associated;
    private final AssociationES association;
    private final MemberES member;
    private final String name;
    private final PersistentAttributeType persistenceType;
    private final Class<T> javaType;
    private final boolean collection;
    private final boolean id;
    private final boolean version;
    private final boolean optional;
    private final Type<T> type;
    private final BindableType bindableType;
    private final Map<AssociationTypeES, AssociationES> associations = new EnumMap<>(AssociationTypeES.class);

    public SingularAttributeESImpl(boolean generated, Generator generator, boolean readOnly, boolean associated, AssociationES association, MemberES member, String name, PersistentAttributeType persistenceType, Class<T> javaType, boolean collection, boolean id, boolean version, boolean optional, Type<T> type, BindableType bindableType) {
        this.generated = generated;
        this.generator = generator;
        this.readOnly = readOnly;
        this.associated = associated;
        this.association = association;
        this.member = member;
        this.name = name;
        this.persistenceType = persistenceType;
        this.javaType = javaType;
        this.collection = collection;
        this.id = id;
        this.version = version;
        this.optional = optional;
        this.type = type;
        this.bindableType = bindableType;
    }
    
    @Override
    public boolean isGenerated() {
        return generated;
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
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
    public MemberES getJavaMember() {
        return member;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return persistenceType;
    }

    @Override
    public ManagedTypeES<X> getDeclaringType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<T> getJavaType() {
        return javaType;
    }

    @Override
    public boolean isAssociation() {
        return associated;
    }

    @Override
    public boolean isCollection() {
        return collection;
    }

    @Override
    public BindableType getBindableType() {
        return bindableType;
    }

    @Override
    public Class<T> getBindableJavaType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Type<T> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.javaType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SingularAttributeESImpl<?, ?> other = (SingularAttributeESImpl<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.javaType, other.javaType)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(AttributeES o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOrdinal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}